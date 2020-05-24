import React from 'react';
import './index.css';
import {withRouter} from 'react-router-dom';
import cron from "node-cron";
import queryString from 'query-string';
import Countdown from 'react-countdown';
import Error from "./Error";
import loading from './loading.gif';

const apiUrl = `${process.env.REACT_APP_CS_ADMIN_URL}/genqrcode`;

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            url: '',
            isLoading: true,
            companyId: '',
            officeId: '',
            nextCountdown: new Date(),
            error: false,
        };

        this.setNewImage = this.setNewImage.bind(this);
        this.setCompanyIdAndOfficeId = this.setCompanyIdAndOfficeId.bind(this);
    }

    setCompanyIdAndOfficeId = (val) => {
        const values = queryString.parse(val);
        const {companyId, officeId} = values;

        this.setState({
            companyId: companyId,
            officeId: officeId
        }, () => {
            this.setNewImage();
        });

    };

    componentWillReceiveProps(nextProps, nextContext) {
        if (this.props.location.search !== nextProps.location.search) {
            this.setCompanyIdAndOfficeId(nextProps.location.search);
        }
    }

    componentDidMount() {
        this.setCompanyIdAndOfficeId(this.props.location.search);

        this.task = cron.schedule("*/5 * * * *", () => {
            this.setNewImage();
        }, null);
    }

    setNewImage = () => {
        if (this.state.error) {
            return;
        }

        this.setState({
            isLoading: true
        });

        const {companyId, officeId} = this.state;
        const now = new Date().getTime();
        // TODO : Remove this, load from config server
        const url = `${apiUrl}?companyId=${companyId}&officeId=${officeId}&reqDate=${now}`;

        console.log(`Load new QR Code at : ${now}\nURL = ${url}`);
        this.setState({
            url: url,
            nextCountdown: now + 300000
        })
    };

    render() {
        const {url, nextCountdown, error, isLoading} = this.state;

        if (error) {
            return <Error/>
        }

        return (
            <>
                <div>
                    <img src={loading}
                         style={{display: isLoading ? 'block' : 'none'}}
                         className={"center-div"}
                         alt={"loading"}/>

                    <img alt={"qrcode"}
                         style={{display: isLoading ? 'none' : 'block'}}
                         className={"center-div"}
                         src={url}
                         onLoad={(event) => {
                             this.setState({
                                 isLoading: false,
                             })
                         }}
                         onError={(event) => {
                             this.task.destroy();
                             this.setState({
                                 error: true
                             })
                         }}
                    />
                </div>
                <div className={"countdown"}
                     style={{display: isLoading ? 'none' : 'block'}}
                >
                    <Countdown date={nextCountdown}/>
                </div>
            </>
        );
    }
}

export default withRouter(App);
