import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {HashRouter, Route} from 'react-router-dom';
import FormPasswordReset from "./resetpassword/FormPasswordReset";
import Invoice from "./Invoice/Invoice";

ReactDOM.render((
    <HashRouter>
        <div>
            <Route exact path="/genqrcode" component={App}/>
            <Route exact path="/resetpassword" component={FormPasswordReset}/>
            <Route exact path="/createorder" component={Invoice}/>
        </div>
    </HashRouter>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.register();
