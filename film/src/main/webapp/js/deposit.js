
function validate() {
    var money = $("#money").val();
    if (!money) {
        alert("金额不能为空！");
        return false;
    }
    if (isNaN(money)) {
        alert("金额不是有效数字！");
        return false;
    }
    return true;
}
