package com.exmek.core.persistence.repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.LeadDefEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.projection.LightweightLeadFlattenLinearStepperMotorProjection;
import com.exmek.core.rest.ConditionClause;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LeadFlattenLinearStepperMotorRepository {

	static enum FieldOwner {
	    STEPPER_MOTOR_ENTITY,
	    LEAD_JOIN
	}
		
    private final EntityManager entityManager;

    private Map<String, FieldOwner> fieldOwnerMap = null;
    
    @PostConstruct
    private void init() {
    	fieldOwnerMap = new HashMap<>();
    	
    	Map<String, Field> stepperMotorEntityFields = ReflectionUtils.collectFields(StepperMotorEntity.class, f -> f.isAnnotationPresent(Column.class));
    	for (Map.Entry<String, Field> entry : stepperMotorEntityFields.entrySet()) {
    		Field f = entry.getValue();
    		fieldOwnerMap.put(f.getName(), FieldOwner.STEPPER_MOTOR_ENTITY);
    	}
    	
    	Map<String, Field> leadJoinFields = ReflectionUtils.collectFields(LeadDefEntity.class, f -> f.isAnnotationPresent(Column.class));
    	for (Map.Entry<String, Field> entry : leadJoinFields.entrySet()) {
    		Field f = entry.getValue();
    		fieldOwnerMap.put(f.getName(), FieldOwner.LEAD_JOIN);
    	}
    }

    public Page<LightweightLeadFlattenLinearStepperMotorProjection> findLightweightLeadFlattenLinearStepperMotorProjections(
            ConditionClause conditionClause,
            String category,
            String series,
            Integer pageNumber,
            Integer pageSize) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // ===== Main query =====
        CriteriaQuery<LightweightLeadFlattenLinearStepperMotorProjection> mainQuery =
                cb.createQuery(LightweightLeadFlattenLinearStepperMotorProjection.class);

        Root<StepperMotorEntity> stepperMotorRoot = mainQuery.from(StepperMotorEntity.class);
        Join<StepperMotorEntity, LeadDefEntity> leadJoin =
                stepperMotorRoot.join("linearStepperMotorLeads", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        // Fixed filters
        if (StringUtils.isNotEmpty(category)) {
        	predicates.add(cb.equal(stepperMotorRoot.get("category"), category));
        }
        if (StringUtils.isNotEmpty(series)) {
        	predicates.add(cb.equal(stepperMotorRoot.get("series"), series));
        }
        Function<String, Path<?>> rootPathResolver = fn -> {
    		return switch (fieldOwnerMap.get(fn)) {
				case STEPPER_MOTOR_ENTITY -> stepperMotorRoot;
				case LEAD_JOIN -> leadJoin;
			};
		};
        if (conditionClause != null) {
        	Predicate dynamicPredicate = JPAUtils.buildPredicate(cb, rootPathResolver, conditionClause, null);
        	if (dynamicPredicate != null) {
        		predicates.add(dynamicPredicate);
        	}
        }
        
        mainQuery.where(predicates.toArray(new Predicate[0]));

        // Projection
        mainQuery.select(
        		cb.construct(LightweightLeadFlattenLinearStepperMotorProjection.class, 
        				resolveSelectionsBasedOnConstructor(LightweightLeadFlattenLinearStepperMotorProjection.class, rootPathResolver)
        ));

        TypedQuery<LightweightLeadFlattenLinearStepperMotorProjection> query = entityManager.createQuery(mainQuery);

        // Pagination if pageNumber and pageSize is presented
        if (pageNumber != null && pageSize != null) {
        	int offset = pageNumber * pageSize;
        	query.setFirstResult(offset);
        	query.setMaxResults(pageSize);
        }
        
        List<LightweightLeadFlattenLinearStepperMotorProjection> data = query.getResultList();

        // ===== Count query =====
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<StepperMotorEntity> countRoot = countQuery.from(StepperMotorEntity.class);
        Join<StepperMotorEntity, LeadDefEntity> countLeadJoin =
                countRoot.join("linearStepperMotorLeads", JoinType.INNER);

        List<Predicate> countPredicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(category)) {
        	countPredicates.add(cb.equal(countRoot.get("category"), category));
        }
        if (StringUtils.isNotEmpty(series)) {
        	countPredicates.add(cb.equal(countRoot.get("series"), series));
        }

        Function<String, Path<?>> countRootPathResolver = fn -> {
    		return switch (fieldOwnerMap.get(fn)) {
				case STEPPER_MOTOR_ENTITY -> countRoot;
				case LEAD_JOIN -> countLeadJoin;
			};
		};
        if (conditionClause != null) {
        	Predicate dynamicPredicate = JPAUtils.buildPredicate(cb, countRootPathResolver, conditionClause, null);
        	if (dynamicPredicate != null) {
        		countPredicates.add(dynamicPredicate);
        	}
        }

        countQuery.select(cb.countDistinct(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        if (pageNumber != null && pageSize != null) {
        	return new PageImpl<>(data, PageRequest.of(pageNumber, pageSize), total);
        } else {
        	return new PageImpl<>(data);
        }
    }
        
    private Selection<?>[] resolveSelectionsBasedOnConstructor(Class<?> clazz, Function<String, Path<?>> rootPathResolver) {
    	Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    	Constructor<?> selectedConstructor = Arrays.stream(constructors)
    			.filter(c -> ObjectUtils.isNotEmpty(c.getParameters()))
    			.findFirst()
    			.get();
    	Parameter[] parameters = selectedConstructor.getParameters();
        Selection<?>[] selections = new Selection<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
        	String fieldName = parameters[i].getName(); // The parameter name must match with field name
        	selections[i] = rootPathResolver.apply(fieldName).get(fieldName);
        }
        return selections;
    }

}