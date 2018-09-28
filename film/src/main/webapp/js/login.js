function validate() {
    var cardNo = $("#cardNo").val();
    var password = $("#password").val();
    if (!cardNo) {
        alert("账号不能为空！");
        return false;
    }
    if (isNaN(cardNo)) {
        alert("非法的账号！");
        return false;
    }
    if (!password) {
        alert("密码不能为空！");
        return false;
    }
    return true;
}