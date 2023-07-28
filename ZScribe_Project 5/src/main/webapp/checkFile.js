let documentsXhr = new XMLHttpRequest();
documentsXhr.open("POST", "GetDocuments");
documentsXhr.send();
documentsXhr.onload = function(){
	documents = [];
	response = JSON.parse(documentsXhr.response);
	allDocuments = response["documents"];
	allDocuments.forEach(function(documentDetails){
		documents.push(documentDetails["document_name"]);
	});
	console.log(documents);
}
documentsXhr.onloadend = function(){
	document.getElementById("input").addEventListener("input", checkForFile);
}

let checkForFile = function(){
	let inputName = document.getElementById("input");
	fileName = inputName.value;
	isAlreadyPresent = documents.includes(fileName);
	if(isAlreadyPresent){
		 alert("Filename "+fileName+" already exists in Zoho Writer");
	}
}