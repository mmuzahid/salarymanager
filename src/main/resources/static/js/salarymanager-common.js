// future use 
function getCsrfData() {
	let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    let data = {
    		header: header,
    		token: token
    	};
    
    return data;
}

