/**
 * 
 */
//window.alert("こんにちは！");
//$(document).ready(function(){ window.alert("こんにちは！"); });
const index_pass = document.getElementById('index_pass');
const pass_display = document.getElementById('pass_display');

pass_display.addEventListener('change', () => {
	if (pass_display.checked) {
		index_pass.type = 'text';
	} else {
		index_pass.type = 'password';
	}
});