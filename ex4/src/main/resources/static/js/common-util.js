function removeSpaces(str) {
    return str.trim();
}

function appendChatMessages(data, emptyMsg= '') {
    const userNameSpan = document.getElementById("user-name");
    const chatField = document.getElementById("chat-field");
    const currentUser = userNameSpan.innerText;

    if (data == null || data.length == 0) {
        chatField.innerHTML = `<p class="text-center text-danger">${emptyMsg}</p>`;
        return;
    }

    let text = '';
    for (const message of data) {
        // Add line break in messages
        let content = message.content.replaceAll("\n", "<br>");
        let time = new Date(message.createdAt);
        let hours = time.getHours();
        let minutes = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();

        let isCurrenUser = currentUser ===  message.userName;

        text += `<div class="d-flex ${isCurrenUser? "flex-row" : "flex-row-reverse"} p-3">
                                <div class="${isCurrenUser? "chat_left" : "chat_right"} ml-2 p-3">
                                    <b>${message.userName}:</b>
                                    <p class="p-0 m-0">${content}</p>
                                    <div class="my-2"><span class="text-muted">${hours}:${minutes}</span></div>
                                </div>
                            </div>`
    }

    chatField.innerHTML = text;
}
