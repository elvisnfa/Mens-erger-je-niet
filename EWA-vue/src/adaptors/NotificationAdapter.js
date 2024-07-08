// see https://www.devglan.com/spring-boot/spring-websocket-integration-example-without-stomp
//import SockJS from "sockjs-client";
export class NotificationAdaptor {
    socketUrl;          // socket-url
    socket = null;      // connection socket (upgraded with async programming compatibility)
    handlersByTopic;    // <string, ((string) => void)[]>

    constructor(socketUrl) {
        this.socketUrl = socketUrl;
        this.handlersByTopic = new Map();

        // use the new websocket protocol or the SockJS library to multiplex across http
        this.socketUrl = socketUrl.replace("https://","wss://");
        this.socket = new WebSocket(this.socketUrl);
        // this.socketUrl = socketUrl.replace("ws://","http://");
        // this.socket = new WebSocket(this.socketUrl);
        console.log(this.socketUrl)


        // introduce a local const for 'this' to refer to 'this NotificationAdaptor' from
        // within (old-style) javascript callback functions that do not have a class scope
        const thisAdaptor = this;

        // upgrade the socket with async capabilities that wait for the handshake to complete
        thisAdaptor.socket._isConnected = new Promise(function(resolve, reject) {
                thisAdaptor.socket.onopen = function (event) {
                    console.log(`Connection successful on ${thisAdaptor.socketUrl}`);
                    // the socket has been connected: resolve the promise
                    resolve(thisAdaptor.socket);
                };
                thisAdaptor.socket.onerror = function (error) {
                    console.log(`ERROR on ${thisAdaptor.socketUrl}:`, error);
                    // socket connection fails in error: reject the promise
                    reject(error);
                };
            }
        );
        thisAdaptor.socket.asyncSend = async function(msg) {
            // wait for completion of the onopen handshake
            await thisAdaptor.socket._isConnected;
            thisAdaptor.socket.send(msg);
        }

        // configure handling of received messages
        thisAdaptor.socket.onmessage = function(e) {
            let msg = e.data.split(' ');
            if (msg[0] === "notify") {
                console.log(`Received notification for ${msg[1]}`)
                thisAdaptor.distributeNotification(msg[1]);
            }
        }
        thisAdaptor.socket.onclose = function(reason) {
            console.log(`Server closed ${thisAdaptor.socketUrl}, reason:`, reason);
            thisAdaptor.socket = null;
        }

        console.log(`Created notification adaptor for ${this.socketUrl}...`);
    }

    async subscribe(topic, handlerCB) {
        if (topic == null) return;

        let handlers = this.handlersByTopic.get(topic.toString());
        if (handlers == null) {
            // subscribe at the server for a new topic
            this.handlersByTopic.set(topic.toString(), [handlerCB])
            await this.socket.asyncSend(`subscribe ${topic}`);
            console.log(`Subscribed notification handler for ${topic}`);
        } else {
            // add a new handlerCB for an existing topic
            const index = handlers.indexOf(handlerCB);
            if (index < 0) {
                handlers.push(handlerCB);
                //console.log(`Added notification handlerCB on ${topic} subscription`);
            }
            else
                console.log(`WARNING duplicate handler subscription for ${topic}`);
        }
    }

    async unsubscribe(topic, handlerCB) {
        if (topic == null) return;

        let handlers = this.handlersByTopic.get(topic.toString());
        if (handlers != null) {
            // TODO remove the given handlerCB from the topic
            //  and unsubscribe from the server if the final handlerCB has been removed


        } else {
            console.log(`ERROR unsubscribing notification handler for ${topic}`)
        }
    }

    notify(topic) {

        // notify all via the server
        this.socket.asyncSend(`notify ${topic}`);

        // notify all subscribed local handlers about the topic
        this.distributeNotification(topic)
    }

    // private
    distributeNotification(topic) {
        // call back all subscribed local handlers about the topic
        let handlers = this.handlersByTopic.get(topic.toString());
        if (handlers != null) {
            for (let handlerCB of handlers) {
                handlerCB(topic);
            }
        } else {
            console.log(`WARNING obsolete notification for ${topic}`)
        }
    }

    async unsubscribeAll(handlerCB) {
        // unsubscribe the given handlerCB from all its topics
        for (let key of this.handlersByTopic.keys()) {
            await this.unsubscribe(key, handlerCB);
        }
    }

    disconnect() {
        if (this.socket != null) {
            this.socket.close();
        }
        console.log(`Disconnected from ${this.socketUrl}`);
        this.socket = null;
    }
}