function $(e){
	return document.getElementById(e);
}

String.prototype.trim = function(){
	return this.replace(/(^\s+)|(\s+$)/g,"");
}
/*
 * 
 * 验证表单域的值是否符合正则表达式指定的格式
 */
function checkFormField(fieldObj,msgObj,re,nullMsg,errorMsg){
	msgObj.innerHTML = "";
	var v = fieldObj.value.replace(/(^\s+)|(\s+$)/g,"");
	var flag = true;
	if(v.length==0){
		msgObj.innerHTML = nullMsg;
		flag = false;
	} else{
		if(!(re.test(v))){
			msgObj.innerHTML = errorMsg;
			flag = false;
		}
	}
	return flag;
}