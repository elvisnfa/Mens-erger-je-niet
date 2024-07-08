
export default class config {

    static async send(url, method, body = false, headers = true){

        let request = {
            method: method
        }

        if (body) {
            if (headers) {
                request.headers = {
                    'Content-type': 'application/json',
                };
            }
            request.body = JSON.stringify(body);
        }

        const response = await fetch(
            url,
            request
        );

        console.log(`API: ${method}`, url);

        return await response.json();
    }

    static async get(url){
        return await this.send(url, "GET");
    }

    static async post(url, body, headers){
        return await this.send(url, "POST", body, headers);
    }

    static async update(url, body, headers){
        return await this.send(url, "PUT", body, headers);
    }


}