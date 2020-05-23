import React from 'react';
import './index.css';
import {withRouter} from 'react-router-dom';
import cron from "node-cron";
import queryString from 'query-string';
import Countdown from 'react-countdown';
import Error from "./Error";

const loadingUrl = 'https://i.redd.it/6eqdnsnk9vwx.gif';
const apiUrl = `${process.env.REACT_APP_CS_ADMIN_URL}/genqrcode`;

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            url: loadingUrl,
            isLoading: false,
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

        cron.schedule("*/3 * * * *", () => {
            this.setNewImage();
        }, null);
    }

    setNewImage = () => {
        if (this.state.error) {
            return;
        }

        this.setState({
            url: loadingUrl
        });

        const {companyId, officeId} = this.state;
        const now = new Date().getTime();
        const url = `${apiUrl}?companyId=${companyId}&officeId=${officeId}&reqDate=${now}`;

        console.log(`Load new QR Code at : ${now}\nURL = ${url}`);
        this.setState({
            url: url,
            nextCountdown: now + 180000
        })
    };

    render() {
        const {url, nextCountdown, error} = this.state;

        if (error) {
            return <Error/>
        }

        return (
            <>
                <div>
                    <img alt={"qrcode"}
                         className={"center-div"}
                         src={url}
                         onError={(event) => {
                             this.setState({
                                 error: true
                             })
                         }}
                    />
                </div>
                <div className={"countdown"}>
                    <Countdown date={nextCountdown}/>
                </div>
            </>
        );
    }
}

export default withRouter(App);
