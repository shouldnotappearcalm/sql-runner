var vm = new Vue({
    el: '#app',
    data: {
        prefix: "/api/v1/user",
        userInfo: {
            userName: "",
            password: ""
        },
        validatePassRule: (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else if (value !== this.password) {
                callback(new Error('请输入一样的密码'));
            } else {
                callback();
            }
        },
        rules: {
            userName: [{
                required: true,
                message: '用户名不为空',
                trigger: 'blur',
                min: 1,
                max: 300
            }],
            password: [{
                required: true,
                message: '密码不为空',
                trigger: 'blur',
                min: 1,
                max: 300
            }],
            confirmPassword: [
                {validator(rule, value, callback, source, options) {
                    if (value === '') {
                        callback(new Error('请输入密码'));
                    } else if (value !== vm.$data.userInfo.password) {
                        callback(new Error('请输入一样的密码'));
                    } else {
                        callback();
                    }
            }}]
        }
    },

    methods: {
        init: function() {
        },

        saveUser: function() {
            var form = this.$refs.userForm;
            var _this = this;
            form.validate().then(function () {
                _this.$Spin.show();
                axios.post(_this.prefix, _this.userInfo).then(function (res) {
                    _this.$Spin.hide();
                    _this.afterSuccess();
                }).catch(function (error) {
                    _this.$Spin.hide();
                    _this.$Message.error({content: error.response.data.message, duration: 5});
                });
            });
        },

        afterSuccess: function() {
            this.$Notice.success({
                title: '提示',
                desc: '注册用户成功，2秒后即将跳转到登录页面'
            });
            setTimeout(function() {
                window.location.href="/html/login.html"
            }, 2000);
        }

    },

    created: function() {
        this.init();
    }
});