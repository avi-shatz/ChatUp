(function () {
    document.addEventListener('DOMContentLoaded', function () {

        document.getElementById('message-form').onsubmit = function (e) {
            if (e.submitter.id === 'search-user') {
                getMessagesByUserName();
            } else if (e.submitter.id === 'search-content'){
                getMessagesByContent();
            }
            e.preventDefault();
        };

        document.getElementById('message-content').onblur = function (e) {
            this.value = removeSpaces(this.value);
        };
    });

    function getMessagesByUserName() {
        let userName = document.getElementById("message-content").value;
        let url = `/get-messages-by-username?userName=${userName}`;
        fetchChatMessages(url);
    }

    function getMessagesByContent() {
        let content = document.getElementById("message-content").value;
        let url = `/get-messages-by-content?content=${content}`;
        fetchChatMessages(url);
    }

    function fetchChatMessages(url) {
        document.getElementById("message-content").value = '';

        fetch(url)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                appendChatMessages(data, "We have not found the messages you asked for.");
            })
            .catch(function (err) {
                console.log('error: ' + err);
            });
    }

})();