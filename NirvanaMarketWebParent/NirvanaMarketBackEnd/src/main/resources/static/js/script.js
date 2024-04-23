/*	
	const buttonCancelEl = document.getElementById("buttonCancel"); 
	
		
	const cancelHandler = e => {
		e.preventDefault();
		
		//alert('CANCELED PREESSED'); 
		console.log('SHOULD NAVIGATE TO /users !')
		console.log(window.location);
		window.location="[[@{/users}]]" ; 
	}
	
	buttonCancelEl.addEventListener("click", cancelHandler); */
	
	
	
	const formSignupForm = document.getElementById("signup")
	console.log(formSignupForm); 
	
	const singupHandler = function(e)
	{
		e.preventDefault();
		console.log('SUBMITTED'); 
		
		
		const url = 'http://localhost:8080/NirvanaMarketBackEnd/users/check_email'; 
	
	const requestBody = 'niritzhak10000@gmail.com'; 
	
	const options = {
		method:'POST' ,
		header:{
			'Content-Type':"text/plain"
		}, 
		body: requestBody
	}; 
	
	
	//MAKE POST REQUEST
	fetch(url, options) 
		.then(response => {
			if(!response) throw new Error('Network response was not ok')
			return response.text();
		})
		.then(responseText => {
			console.log('Response: ', responseText)
		})
		.catch(err => {
			console.error('There was a problem with the fetch operation:', err)
		})
	
		
	}
	
	
	formSignupForm.addEventListener('submit', singupHandler);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	