(function () {
    document.addEventListener('DOMContentLoaded', function () {
        updateChatState()
        // Invoke the request every 2 seconds.
        const fetchInterval = 2000; // 2 seconds.
        setInterval(updateChatState, fetchInterval);

        document.getElementById('message-form').onsubmit = function (e) {
            sendMessage();
            e.preventDefault();
        };

        document.getElementById('message-content').onblur = function (e) {
            this.value = removeSpaces(this.value);
        };

    });

    function updateChatState() {
        fetchAndUpdateConnectedUsers();
        fetchChatMessages()
    }

    function fetchChatMessages() {
        fetch('/get-last-messages')
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                appendChatMessages(data);
            })
            .catch(function (err) {
                console.log('error: ' + err);
            });
    }

    function sendMessage() {
        let contentElem = document.getElementById("message-content");
        let userName = document.getElementById("userName").value.trim();
        let content = contentElem.value.trim();

        contentElem.value = '';

        const message = new FormData();
        message.append("userName", userName);
        message.append("content", content);

        fetch('/send-message', {
            method: 'post',
            body: message
        }).then(function (res) {
            fetchChatMessages();
        }).catch(function (err) {
            console.log('error: ' + err);
        });

    }

    function fetchAndUpdateConnectedUsers() {
        fetch('/get-connected-users')
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                updateConnectedUsers(data);
            }).catch(function (err) {
            console.log('error: ' + err);
        });
    }

    function updateConnectedUsers(data) {
        let connectedUsersDiv = document.getElementById("connected-users");

        let text = ''
        for (const user of data) {
            text += `<li class="user list-group-item">
                                <img height="50"
                                     src="https://img.icons8.com/color/48/000000/circled-user-female-skin-type-7.png"
                                     alt="user img" width="50">
                                <p class="pl-3 d-inline-block">${user.userName}</p>
                            </li>`
        }

        connectedUsersDiv.innerHTML = text;
    }

})();