function EventEmitter() {
    let listeners = [];

    function subscribe(handler) {
        listeners.push(handler);
    }

    function notify(...data) {
        for (let i = 0; i < listeners.length; i++) {
            listeners[i](...data);
        }
    }

    return {
        subscribe,
        notify
    }

}

