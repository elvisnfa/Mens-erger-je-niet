// see https://www.npmjs.com/package/fetch-intercept
import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static theInstance; // the singleton instance that has been registered
    sessionService;     // the sessionService which tracks the authorisation
    unregister;         // callback function to unregister this instance
    toast;

    constructor(sessionService, router, toast) {
        FetchInterceptor.theInstance = this;
        this.sessionService = sessionService;
        // fetchIntercept does not register the object closure, only the methods as functions
        this.unregister = fetchIntercept.register(this);
        this.$router = router;
        this.toast = toast;
        console.log("FetchInterceptor has been registered.");
    }

    request(url, options) {
        let token = FetchInterceptor.theInstance.sessionService.currentToken;
        console.log("FetchInterceptor request: ", url, options, token);

        if (token == null) {
            return [url, options];
        } else if (options == null) {
            return [url, {headers: {Authorization: token}}]
        } else {
            let newOptions = {...options};
            // TODO combine existing headers with new Authorization header
            newOptions.headers = {
                ...newOptions.headers,
                Authorization: token,
                'X-Forwarded-For': '1.1.1.1',
            };


            // console.log("FetchInterceptor request: ", url, newOptions.headers);
            return [url, newOptions];
        }
    }

    response(response) {
        console.log("FetchInterceptor response: ", response);
        FetchInterceptor.theInstance.tryRecoverNewJWToken(response);
        if (response.status >= 400 && response.status < 600) {
            FetchInterceptor.theInstance.handleErrorInResponse(response);
        }
        return response;
    }

    requestError(error) {
        // Called when an error occured during another 'request' interceptor call
        console.log("FetchInterceptor requestError: ", error);
        return Promise.reject(error);
    }

    responseError(error) {
        // Handle a fetch error
        console.log("FetchInterceptor responseError: ", error);
        return Promise.reject(error);
    }

    async handleErrorInResponse(response) {
        if (response.status === 401) {
            // TODO handle an UNAUTHORISED response
            let refresh = await this.sessionService.refresh();

            if (!refresh.ok){
                await this.sessionService.signOut();
                // unauthorised request, redirect to signIn page
                // this.router.navigate(['/sign-out']);    // ng-router
                this.$router.push('/Loginpage');   // vue-router
                this.toast.error("Session is expired or unauthorized")
            }else{
                alert("New session for 20min")
                location.reload();
            }

        } else if (response.status !== 406) {
            // 406='Not Acceptable' error is used for logon failure
            // TODO handle any other error
        }
    }

    getCookie(cname) {
        let name = cname + "=";
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for(let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    async tryRecoverNewJWToken(response) {
        // TODO check the response on availability of a JWT
        //  and request the session service to save that
        let token = response.headers.get("Authorization")
        if (token != null) {
            this.sessionService.saveTokenIntoBrowserStorage(token, null)
        }

    }
}
