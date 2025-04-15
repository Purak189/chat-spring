'use strict';

let usernamePage = document.querySelector('#username-page');
let chatPage = document.querySelector('#chat-page');
let usernameForm = document.querySelector('#usernameForm');
let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#message');
let messageArea = document.querySelector('#messageArea');
let connectingElement = document.querySelector('.connecting');

let stompClient = null;
let username = null;
let userId = null;
let projectId = null;

let colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    userId = document.querySelector('#userId').value.trim();
    projectId = document.querySelector('#projectId').value.trim();

    if (username && userId && projectId) {
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }

    event.preventDefault();
}

function onConnected() {
    // Primero suscribirse a posibles errores
    stompClient.subscribe('/user/queue/errors', function (errorMessage) {
        alert("🚫 Error: " + errorMessage.body);
        disconnectAndReset();
    });

    // Luego suscribirse al topic del proyecto
    stompClient.subscribe(`/topic/project/${projectId}`, onMessageReceived);

    // Finalmente enviar el mensaje JOIN para validación
    stompClient.send(`/app/chat.join.${projectId}`, {}, JSON.stringify({
        sender: username,
        senderId: userId,
        projectId: projectId,
        type: 'JOIN'
    }));
}

function disconnectAndReset() {
    if (stompClient !== null) {
        stompClient.disconnect();
        stompClient = null;
    }
    usernamePage.classList.remove('hidden');
    chatPage.classList.add('hidden');
    connectingElement.textContent = 'Connecting...';
    connectingElement.style.color = '';
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    let messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            senderId: userId,
            content: messageContent,
            type: 'CHAT'
        };

        stompClient.send(`/app/chat.sendMessage.${projectId}`, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }

    event.preventDefault();
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);

    if (message.type === 'JOIN' && message.senderId === userId) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        connectingElement.classList.add('hidden');
    }

    let messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    let index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
