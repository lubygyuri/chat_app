function scrollDown() {
    try {
        let container = document.getElementById("messages");
        container.scroll(0, container.scrollHeight);
    } catch (err) {
        return null;
    }
}

