    function validate() {
        var money = $("#money").val();
        var name = $("#name").val();
        var cardNo = $("#cardNo").val();
        if (!cardNo) {
            alert("卡号不能为空！");
            return false;
        }
        if (!name) {
            alert("请输入转入用户名！");
            return false;
        }
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