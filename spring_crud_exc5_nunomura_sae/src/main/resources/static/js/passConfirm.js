/**
 * 
 */
const pass1 = document.getElementById('regist_pass');
const pass2 = document.getElementById('regist_pass_confirm');
const msg = document.getElementById('confirm_errMessage');
//const btn = document.getElementById('linkBtn');

// パスワード一致チェック関数
const checkPassword = () => {
	const val1 = pass1.value;
	const val2 = pass2.value;

	if (val1 === "" && val2 === "") {
		msg.textContent = "";
		msg.className = "";
//		btn.disabled = true;
		return;
	}

	if (val1 === val2) {
		msg.textContent = "";
		msg.className = "valid";
		// 一致したらボタンのロックを解除（活性化）
//		btn.disabled = false;
	} else {
		msg.textContent = "パスワードが一致していません";
		msg.className = "confirm_errMessage";
		// 不一致ならロックする
//		btn.disabled = true;
	}
};

pass1.addEventListener('input', checkPassword);
pass2.addEventListener('input', checkPassword);