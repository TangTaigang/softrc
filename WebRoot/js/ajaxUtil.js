function createAjaxObj(){
	var req;
	if(window.XMLHttpRequest){
		req = new XMLHttpRequest();
	} else{
		req = new ActiveXObject("Msxml2.XMLHTTP");  //ie
	}
	return req;
}

function sendAjaxReq(method,url,param,asychn,handle200,loading,handle404,handle500){
	//创建XMLHttpRequest对象
	var req = createAjaxObj();
	req.onreadystatechange = function(){
		if(4==req.readyState){ //表示服务器端的响应数据已经成功接收
			if(200==req.status){
				if(handle200){
					handle200(req.responseText);
				}
			} else if(404==req.status){
				if(handle404){
					handle404();
				}
			} else if(500==req.status){
				if(handle500){
					handle500();
				}
			}
		} else{
			if(loading){
				loading();
			}
		}
	}
	
	if("get"==method.toLowerCase()){
		var s = (param==null)?"":"?"+param;
		req.open(method,url+s,asychn);
		req.send(null);
	} else if("post"==method.toLowerCase()){
		req.open(method,url,asychn);
		req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		req.send(param);
	}
}

function sendAjaxReqGetXML(method,url,param,asychn,handle200,loading,handle404,handle500){
	//创建XMLHttpRequest对象
	var req = createAjaxObj();
	req.onreadystatechange = function(){
		if(4==req.readyState){ //表示服务器端的响应数据已经成功接收
			if(200==req.status){
				if(handle200){
					var xmlDoc = req.responseXML;
					if(xmlDoc==null){
						var result = req.responseText;
						xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
						xmlDoc.loadXML(result);
					}
					handle200(xmlDoc);
				}
			} else if(404==req.status){
				if(handle404){
					handle404();
				}
			} else if(500==req.status){
				if(handle500){
					handle500();
				}
			}
		} else{
			if(loading){
				loading();
			}
		}
	}
	
	if("get"==method.toLowerCase()){
		req.open(method,url+((param==null)?"":"?"+param),asychn);
		req.send(null);
	} else if("post"==method.toLowerCase()){
		req.open(method,url,asychn);
		req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		req.send(param);
	}
	
}

