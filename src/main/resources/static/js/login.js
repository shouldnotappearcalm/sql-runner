var vm = new Vue({
    el: '#app',
    data: {
        logout: false,
        error: false
    },

    methods: {
        init: function() {
            var query = window.location.search.substring(1);
            if (query == 'logout') {
                this.logout = true;
            } else if (query == 'error') {
                this.error = true;
            }
        },
        // 注册
        register: function() {
            window.location.href = "/html/registration.html";
        }

    },

    created: function() {
        this.init();
    }
});