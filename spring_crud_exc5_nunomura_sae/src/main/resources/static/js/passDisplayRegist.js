/**
 * 
 */
const regist_pass = document.getElementById('regist_pass');
const pass_display_regist = document.getElementById('pass_display_regist');

pass_display_regist.addEventListener('change', () => {
	if (pass_display_regist.checked) {
		regist_pass.type = 'text';
	} else {
		regist_pass.type = 'password';
	}
});

const regist_pass_confirm = document.getElementById('regist_pass_confirm');
const pass_display_regist_confirm = document.getElementById('pass_display_regist_confirm');

pass_display_regist_confirm.addEventListener('change', () => {
	if (pass_display_regist_confirm.checked) {
		regist_pass_confirm.type = 'text';
	} else {
		regist_pass_confirm.type = 'password';
	}
});