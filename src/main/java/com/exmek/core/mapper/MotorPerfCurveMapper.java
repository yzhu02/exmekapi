package com.exmek.core.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.exmek.commons.utils.MiscUtils;
import com.exmek.core.commons.model.CurveLine;
import com.exmek.core.commons.model.Point;
import com.exmek.core.config.CurveCoordinate;
import com.exmek.core.config.MotorConfigProvider;
import com.exmek.core.error.BizRuntimeException;
import com.exmek.core.error.ErrorCode;
import com.exmek.core.model.LinearStepperMotorPerfCurve;
import com.exmek.core.model.LinearStepperMotorPerfCurve.SpeedMeasure;
import com.exmek.core.model.MotorPerfCurve;
import com.exmek.core.persistence.entity.AbstractMotorPerfMeasurementEntity;

@Component
public class MotorPerfCurveMapper {
	
	@Autowired
	private MotorConfigProvider motorConfigProvider;

	public <E extends AbstractMotorPerfMeasurementEntity> List<MotorPerfCurve> mapToPerfCurveModels(Set<E> entities, String model) {
		if (entities == null) {
			return null;
		}
		List<MotorPerfCurve> models = new ArrayList<>();
		for (E entity : entities) {
			MotorPerfCurve perfCurve = mapToPerfCurveModel(entity, model);
			if (perfCurve != null) {
				models.add(perfCurve);
			}
		}
		return models;
	}

	public MotorPerfCurve mapToPerfCurveModel(AbstractMotorPerfMeasurementEntity entity, String model) {
		if (entity == null) {
			return null;
		}
		MotorPerfCurve perfCurve = new MotorPerfCurve();
		perfCurve.setTitle(entity.getTitle());
		List<CurveCoordinate> dcMotorCurveCoordinates = motorConfigProvider.getMotorCurveCoordinates(model);
		if (!CollectionUtils.isEmpty(dcMotorCurveCoordinates)) {
			String[] columnNames = MiscUtils.split(entity.getVariables(), ",");
			String[] mConditions = MiscUtils.split(entity.getConditions(), ",");
			BigDecimal[][] mValues = MiscUtils.parseCSVLikeValues(entity.getValues(),
					rows -> new BigDecimal[rows][], cells -> new BigDecimal[cells], s -> MiscUtils.parseBigDecimalValue(s));
			List<CurveLine> curveLines = new ArrayList<>();
			for (int i = 0; i < dcMotorCurveCoordinates.size(); i++) {
				CurveCoordinate cc = dcMotorCurveCoordinates.get(i);
				CurveLine cLine = new CurveLine();
				if (mConditions != null && mConditions.length >= i) {
					cLine.setName(mConditions[i]); //stepper motor may use dynamic condition as line name
				} else {
					cLine.setName(cc.getName()); //dc motor use static pre-configured name as line name
				}
				Triple<String, String, Integer> configuredXColNameByInx = extractNameByIndex(cc.getX());
				Triple<String, String, Integer> configuredYColNameByInx = extractNameByIndex(cc.getY());
				String xColName = configuredXColNameByInx.getMiddle();
				String yColName = configuredYColNameByInx.getMiddle();
				cLine.setXAxisName(xColName);
				cLine.setYAxisName(yColName);
				int xColInx = findIndex(columnNames, configuredXColNameByInx);
				int yColInx = findIndex(columnNames, configuredYColNameByInx);
				if (xColInx >= 0 && yColInx >= 0) {
					for (int r = 0; r < mValues.length; r++) {
						if ((xColInx < mValues[r].length && mValues[r][xColInx] != null)
								&& (yColInx < mValues[r].length && mValues[r][yColInx] != null)) {
							cLine.addPoint(Point.of(mValues[r][xColInx], mValues[r][yColInx]));
						}
					}
				}
				curveLines.add(cLine);
			}
			perfCurve.setCurveLines(curveLines);
		}
		return perfCurve;
	}
	
	//Example: 
	// Speed(rpm) -> [Speed(rpm), Speed(rpm), null]
	// Speed(rpm)[0] -> [Speed(rpm)[0], Speed(rpm), 0]
	private Triple<String, String, Integer> extractNameByIndex(String name) {
		if (name == null) {
			return null;
		}
		name = name.trim();
		if (!name.endsWith("]")) {
			return Triple.of(name, name, null);
		}
		int inx = name.indexOf('[');
		if (inx > 0 && inx < name.length() - 2) {
			return Triple.of(name, name.substring(0, inx), Integer.valueOf(name.substring(inx + 1, name.length() - 1)));
		}
		return Triple.of(name, name, null);
	}

	private int findIndex(String[] sourceArray, Triple<String, String, Integer> tFinding) {
		if (sourceArray == null || sourceArray.length < 1) {
			return -1;
		}
		String targetToFind = tFinding.getLeft();
		int foundInx = MiscUtils.findIndex(sourceArray, targetToFind);
		if (foundInx >= 0) {
			return foundInx;
		}
		Integer matchingInx = tFinding.getRight();
		if (matchingInx == null || matchingInx < 0) {
			return foundInx;
		}
		int matchingCounter = 0;
		targetToFind = tFinding.getMiddle();
		for (int i = 0; i < sourceArray.length; i++) {
			if (Objects.equals(sourceArray[i], targetToFind)) {
				if (matchingInx != null && matchingInx >= 0) {
					if (matchingCounter == matchingInx) {
						return i;
					}
				} else {
					return i;
				}
				matchingCounter++;
			}
		}
		return -1;
	}
	
	public <E extends AbstractMotorPerfMeasurementEntity> List<LinearStepperMotorPerfCurve> mapToLinearStepperMotorPerfCurveModels(Set<E> entities, String model) {
		if (entities == null) {
			return null;
		}
		List<LinearStepperMotorPerfCurve> models = new ArrayList<>();
		for (E entity : entities) {
			LinearStepperMotorPerfCurve perfCurve = mapToLinearStepperMotorPerfCurveModel(entity, model);
			if (perfCurve != null) {
				models.add(perfCurve);
			}
		}
		return models;
	}

	public LinearStepperMotorPerfCurve mapToLinearStepperMotorPerfCurveModel(AbstractMotorPerfMeasurementEntity entity, String model) {
		if (entity == null) {
			return null;
		}
		LinearStepperMotorPerfCurve perfCurve = new LinearStepperMotorPerfCurve();
		perfCurve.setTitle(entity.getTitle());
		
		//'pps, r/min, LinearSpeed[0.3048:AF, 0.6096:AA, 1.2192:B, 2:G, 4:M, 8:T](mm/s), Thrust[0.3048:AF, 0.6096:AA, 1.2192:B, 2:G, 4:M, 8:T](kg)'
		
		String variablesStr = entity.getVariables();
		if (ObjectUtils.isEmpty(variablesStr)) {
			return perfCurve;
		}
		String linearSpeedPrefix = "LinearSpeed[";
		String thrustPrefix = "Thrust[";
		List<String> columnNames = new ArrayList<>();
		Map<String, Integer> linearSpeedColIndexMap = new LinkedHashMap<>();
		Map<String, Integer> thrustColIndexMap = new LinkedHashMap<>();

		String narrowedVarsStr = variablesStr;
		int linearSpeedFromInx = narrowedVarsStr.indexOf(linearSpeedPrefix);
		int linearSpeedEndInx = narrowedVarsStr.indexOf(']', linearSpeedFromInx + linearSpeedPrefix.length());
		if (linearSpeedFromInx == -1 || linearSpeedEndInx == -1) {
			throw new BizRuntimeException("Unable to find " + linearSpeedPrefix + "] for linear stepper motor performance curve data. ",
					ErrorCode.ERR_CODE_LINEAR_STEPPER_MOTOR_PERF_CURVE_DATA_MALFORMED);
		}
		String linearSpeedEnclosingPlaceholder = "__LINEARSPEEDCOLS-PLACEHOLDER__";
		String linearSpeedEnclosingCols = narrowedVarsStr.substring(linearSpeedFromInx + linearSpeedPrefix.length(), linearSpeedEndInx);
		narrowedVarsStr = narrowedVarsStr.substring(0, linearSpeedFromInx) + linearSpeedPrefix + linearSpeedEnclosingPlaceholder + narrowedVarsStr.substring(linearSpeedEndInx);
		
		int thrustFromInx = narrowedVarsStr.indexOf(thrustPrefix);
		int thrustEndInx = narrowedVarsStr.indexOf(']', thrustFromInx + thrustPrefix.length());
		if (thrustFromInx == -1 || thrustEndInx == -1) {
			throw new BizRuntimeException("Unable to find " + thrustPrefix + "] for linear stepper motor performance curve data. ",
					ErrorCode.ERR_CODE_LINEAR_STEPPER_MOTOR_PERF_CURVE_DATA_MALFORMED);
		}
		String thrustEnclosingPlaceholder = "__THRUSTCOLS-PLACEHOLDER__";
		String thrustEnclosingCols = narrowedVarsStr.substring(thrustFromInx + thrustPrefix.length(), thrustEndInx);
		narrowedVarsStr = narrowedVarsStr.substring(0, thrustFromInx) + thrustPrefix + thrustEnclosingPlaceholder + narrowedVarsStr.substring(thrustEndInx);
		
		String linearSpeedUnit = "";
		String thrustUnit = "";
		String[] columnSegments = narrowedVarsStr.split(",");
		int colIndex = 0;
		for (String columnSegment : columnSegments) {
			columnSegment = columnSegment.trim();
			if (columnSegment.startsWith(linearSpeedPrefix)) {
				String[] cols = linearSpeedEnclosingCols.split(",");
				for (String col : cols) {
					col = col.trim();
					columnNames.add(col);
					linearSpeedColIndexMap.put(col, colIndex++);
				}
				int unitEndInx = columnSegment.lastIndexOf(')');
				int unitFromInx = columnSegment.lastIndexOf("(", unitEndInx - 1);
				if (unitFromInx > 0 && unitEndInx > unitFromInx) {
					linearSpeedUnit = columnSegment.substring(unitFromInx + 1, unitEndInx);
				}
			} else if (columnSegment.startsWith(thrustPrefix)) {
				String[] cols = thrustEnclosingCols.split(",");
				for (String col : cols) {
					col = col.trim();
					columnNames.add(col);
					thrustColIndexMap.put(col, colIndex++);
				}
				int unitEndInx = columnSegment.lastIndexOf(')');
				int unitFromInx = columnSegment.lastIndexOf("(", unitEndInx - 1);
				if (unitFromInx > 0 && unitEndInx > unitFromInx) {
					thrustUnit = columnSegment.substring(unitFromInx + 1, unitEndInx);
				}
			} else {
				columnNames.add(columnSegment);
				colIndex++;
			}
		}
		
		BigDecimal[][] mValues = MiscUtils.parseCSVLikeValues(entity.getValues(),
				rows -> new BigDecimal[rows][], cells -> new BigDecimal[cells], s -> MiscUtils.parseBigDecimalValue(s));
		List<CurveLine> curveLines = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : thrustColIndexMap.entrySet()) {
			String colName = entry.getKey();
			String[] colNameParts = colName.split(":");
			BigDecimal keyCoefficient = new BigDecimal(colNameParts[0]);
			Integer thrustColIndex = entry.getValue();
			CurveLine cLine = new CurveLine();
			cLine.setName(colName.replace(':', '(') + ")");
//			cLine.setXAxisName(linearSpeedUnit);
			cLine.setYAxisName(thrustUnit);
			int xColInx = linearSpeedColIndexMap.get(colName);
			int yColInx = thrustColIndex;
			if (xColInx >= 0 && yColInx >= 0) {
				for (int r = 0; r < mValues.length; r++) {
					if (xColInx < mValues[r].length && yColInx < mValues[r].length) {
						cLine.addPoint(Point.of(mValues[r][xColInx].divide(keyCoefficient, RoundingMode.HALF_UP), mValues[r][yColInx]));
					}
				}
			}
			curveLines.add(cLine);	
		}
		perfCurve.setCurveLines(curveLines);
		
		SpeedMeasure speedMeasure = new SpeedMeasure();
		String[] speedUnits = new String[2 + linearSpeedColIndexMap.size()];
		BigDecimal[][] speedValues = new BigDecimal[mValues.length][speedUnits.length];
		speedUnits[0] = "pps";
		speedUnits[1] = "r/min";
		colIndex = 2;
		for (Map.Entry<String, Integer> entry : linearSpeedColIndexMap.entrySet()) {
			String colName = entry.getKey();
			String[] colNameParts = colName.split(":");
			String speedSymbol = colNameParts[1];
			speedUnits[colIndex++] = speedSymbol + "(" + linearSpeedUnit + ")";
			int linearSpeedColIndex = entry.getValue();
			for (int r=0; r<mValues.length; r++) {
				speedValues[r][linearSpeedColIndex] = mValues[r][linearSpeedColIndex];
			}
		}
		for (int r=0; r<mValues.length; r++) {
			int colInx = columnNames.indexOf(speedUnits[0]);
			if (colInx >= 0) {
				speedValues[r][0] = mValues[r][colInx];
			}
			colInx = columnNames.indexOf(speedUnits[1]);
			if (colInx >= 0) {
				speedValues[r][1] = mValues[r][colInx];
			}
		}
		speedMeasure.setUnits(speedUnits);
		speedMeasure.setValues(speedValues);
		perfCurve.setSpeedMeasure(speedMeasure);
		
		return perfCurve;
	}

}
