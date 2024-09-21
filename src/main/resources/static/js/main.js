const apiBaseUrl = "http://localhost:8080/api";
const apiKey = 'exmek_api_key';

let motorFilterFields = [
	["controlType", "byEquals"],
	["voltageCurrentType", "byEquals"],
	["rotorType", "byEquals"],
	["length", "byBetween"],
	["ratedVoltage", "byBetween"],
	["ratedPower", "byBetween"],
	["ratedRotatingSpeed", "byBetween"],
	["ratedCurrent", "byBetween"],
	["ratedTorque", "byBetween"],
	["outputPower", "byBetween"]
];

function clickSearchMotors() {
	let conditions = prepareConditions();
	performSearchMotors(conditions);
}

function prepareConditions() {
	let conditions = [];
	for (let i = 0; i < motorFilterFields.length; ++i) {
		let fieldName = motorFilterFields[i][0];
		let compareType = motorFilterFields[i][1];
		if ("byEquals" == compareType) {
			let fieldElem = document.getElementById("_filter_"  + fieldName);
			if (fieldElem != undefined && fieldElem != null) {
				let value = fieldElem.value;
				if (value != undefined && value != null && value != "") {
					conditions.push(fieldName + "=="  + value);
				}
			}
		} else if ("byBetween" == compareType) {
			let fieldFromElem = document.getElementById("_filter_"  + fieldName + "_from");
			if (fieldFromElem != undefined && fieldFromElem != null) {
				let valueFrom = fieldFromElem.value;
				if (valueFrom != undefined && valueFrom != null && valueFrom != "") {
					conditions.push(fieldName + ">=" + valueFrom);
				}
			}
			let fieldToElem = document.getElementById("_filter_"  + fieldName + "_to");
			if (fieldToElem != undefined && fieldToElem != null) {
				let valueTo = fieldToElem.value;
				if (valueTo != undefined && valueTo != null && valueTo != "") {
					conditions.push(fieldName + "<=" + valueTo);
				}
			}
		}
	}
	return conditions;
}

function performSearchMotors(conditions) {
	let reqPayload = {
		"conditions": conditions	
	};
	let requestOptions = {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Authorization': `${apiKey}`
		},
		body: JSON.stringify(reqPayload)
	};

	fetch(apiBaseUrl + "/motors/search", requestOptions)
	.then(response => {
		if (!response.ok) {
			throw new Error("Unable to receive response from /motors/search service api.");
		}
		return response.json();
	})
	.then(data => {
		refreshSearchResultOnPage(data);
	})
	.catch(err => {
		console.error("Error: ", err);
	});
}

function refreshSearchResultOnPage(data) {
	let htmlResultContent = "";
	for (let i = 0; i < data.length; ++i) {
		let motor = data[i];
		htmlResultContent += 
		`
		<tr result-table-row="" ng-repeat="product in resCtrl.results" ng-class-odd="'is-odd'" ng-class-even="'is-even'" class="ng-scope ${(i+1) % 2 == 0 ? 'is-even' : 'is-odd'}">
            <td>
                <a ng-href="/motors/${motor.PN ?? motor.pn}" title="${motor.PN ?? motor.pn}" href="/motors/${motor.PN ?? motor.pn}">
                    <img ng-if="!(product.image | isArray)" ng-src="https://en.nanotec.com/fileadmin/_processed_/d/4/csm_DF20_d0fb15417b.jpg" class="table-img-product ng-scope" alt="${motor.PN ?? motor.pn}" width="32" src="https://en.nanotec.com/fileadmin/_processed_/d/4/csm_DF20_d0fb15417b.jpg" />
                </a>
                <a ng-href="/products/${motor.PN ?? motor.pn}" title="${motor.PN ?? motor.pn}" href="/motors/${motor.PN ?? motor.pn}">
                    <u class="ng-binding">${motor.PN ?? motor.pn}</u>
                </a>&nbsp;
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">-</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.frameSize ?? '-'}&nbsp;${motor.frameSizeUnit ?? '-'}</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.length ?? '-'}&nbsp;${motor.lengthUnit ?? '-'}</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.ratedVoltage ?? '-'}&nbsp;${motor.ratedVoltageUnit ?? '-'}</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.ratedPower ?? '-'}&nbsp;${motor.ratedPowerUnit ?? '-'}</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.ratedRotatingSpeed ?? '-'}&nbsp;${motor.ratedRotatingSpeedUnit ?? '-'}</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.ratedCurrent ?? '-'}&nbsp;${motor.ratedCurrentUnit ?? '-'}</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.ratedTorque ?? '-'}&nbsp;${motor.ratedTorqueUnit ?? '-'}</span>
            </td>
            <td ng-repeat="attribute in resCtrl.columnAttributes" ng-if="!attribute.hideInTableHead" class="ng-scope">
                <span ng-bind-html="(product.attributes | filter:{uid: attribute.uid}:true)[0].value | attributeValue: attribute" class="ng-binding">${motor.outputPower ?? '-'}&nbsp;${motor.outputPowerUnit ?? '-'}</span>
            </td>
            
            <td align="center" ng-if="resCtrl.hasCurves()" class="ng-scope">
            </td>
            
            <td align="right">
                <a class="table-btn-action btn-primary" ng-href="/motors/${motor.PN ?? motor.pn}" title="${motor.PN ?? motor.pn}" href="/motors/${motor.PN ?? motor.pn}">
                    <i class="icon-squared-medium-right"></i>
                </a>
                <button class="table-btn-arrows" ng-class="{'is-open': resCtrl.expandRowDetails[$i]}" ng-click="resCtrl.expandRowDetails[$i] = !resCtrl.expandRowDetails[$i]">
                </button>
            </td>
        </tr>
        `;
	}
	
	document.getElementById("total-results").innerHTML = data.length;
	document.getElementById("searchResultsContent").innerHTML = htmlResultContent;
}