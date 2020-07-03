import React from 'react';
import {ErrorMessage, Field, Form, Formik} from 'formik';
import * as Yup from 'yup';
import queryString from "query-string";
import axios from 'axios';
import Error from "../Error";

const apiUrl = `${process.env.REACT_APP_CS_ADMIN_URL}/resetpassword`;

export default class FormPasswordReset extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            companyId: '',
            clientId: 0,
            username: '',
            token: '',
            error: false,
        };

        this.setParams = this.setParams.bind(this);
        this.resetPassword = this.resetPassword.bind(this);
    }

    resetPassword = (values) => {
        const {password} = values;
        const {clientId, username, token} = this.state;
        let {companyId} = this.state;
        if (!companyId) {
            companyId = '';
        }

        const data = {
            clientId: clientId,
            companyId: companyId,
            username: username,
            password: password,
            token: token,
        };

        axios.post(apiUrl, data, {})
            .then(response => {
                if (response.data.returnCode === 1) {
                    alert(response.data.returnMessage);
                    setTimeout(() => {
                        window.open("", "_self");
                        window.close();
                    }, 3000);
                }
                else {
                    alert(response.data.returnMessage);
                }
        }).catch(error => {
            alert("Hệ thống có lỗi. Vui lòng thử lại sau");
            console.error(error);
        })
    };

    setParams = (val) => {
        this.setState({
            error: false,
        });

        const values = queryString.parse(val);
        const {companyId, username, token} = values;
        let clientId = parseInt(values.clientId);

        if ((clientId < 1 || clientId > 3) ||
            !username || username === '' ||
            !token || token === '' ||
            ((clientId === 2 || clientId === 1) && (!companyId || companyId === ''))) {
            this.setState({
                error: true,
            });

            return;
        }

        this.setState({
            companyId: companyId,
            username: username,
            clientId: clientId,
            token: token,
        });
    };

    componentWillReceiveProps(nextProps, nextContext) {
        if (this.props.location.search !== nextProps.location.search) {
            this.setParams(nextProps.location.search);
        }
    }

    componentDidMount() {
        this.setParams(this.props.location.search);
    }

    render() {
        const {error} = this.state;
        if (error) {
            return <Error/>
        }

        return (
            <div className="jumbotron">
                <div className="container">
                    <div className="row">
                        <div className="col-md-6 offset-md-3">
                            <h3 style={{textAlign: 'center', marginBottom: '20px'}}>Thiết lập lại mật khẩu</h3>
                            <Formik
                                initialValues={{
                                    password: '',
                                    confirmPassword: ''
                                }}
                                validationSchema={Yup.object().shape({
                                    password: Yup.string()
                                        .min(6, 'Mật khẩu phải dài ít nhất 6 kí tự')
                                        .required('Mật khẩu không được rỗng'),
                                    confirmPassword: Yup.string()
                                        .oneOf([Yup.ref('password'), null], 'Mật khẩu không khớp')
                                        .required('Nhập lại mật khẩu không được rỗng')
                                })}
                                onSubmit={fields => {
                                    this.resetPassword(fields);
                                }}
                                render={({errors, status, touched}) => (
                                    <Form>
                                        <div className="form-group">
                                            <label htmlFor="password">Mật khẩu mới</label>
                                            <Field name="password" type="password"
                                                   className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')}/>
                                            <ErrorMessage name="password" component="div" className="invalid-feedback"/>
                                        </div>
                                        <div className="form-group">
                                            <label htmlFor="confirmPassword">Nhập lại mật khẩu</label>
                                            <Field name="confirmPassword" type="password"
                                                   className={'form-control' + (errors.confirmPassword && touched.confirmPassword ? ' is-invalid' : '')}/>
                                            <ErrorMessage name="confirmPassword" component="div"
                                                          className="invalid-feedback"/>
                                        </div>
                                        <div className="form-group">
                                            <button type="submit" className="btn btn-primary mr-2">Xác nhận</button>
                                            <button type="reset" className="btn btn-secondary">Xóa</button>
                                        </div>
                                    </Form>
                                )}
                            />
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
