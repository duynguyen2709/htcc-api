import React, {Component} from 'react'
import styles from './Invoice.module.scss'

import LineItems from './LineItems'
import Error from "../Error";
import loading from "../loading.gif";
import queryString from "query-string";
import axios from "axios";

const createOrderUrl = `${process.env.REACT_APP_CS_ADMIN_URL}/createorder`;
const submitTransUrl = `${process.env.REACT_APP_CS_ADMIN_URL}/submittrans`;

class Invoice extends Component {

    locale = 'vi-VN';
    currency = 'VND';

    state = {
        isLoading: true,
        order: {},

        paymentName: '',
        paymentId: '',
        paymentCycleType: 1,
    };

    createOrder = () => {
        const {params} = this.state;

        axios.post(createOrderUrl, params, {})
            .then(response => {
                if (response.data.returnCode === 1) {
                    console.log(response.data.data);
                    const order = response.data.data;
                    let numEmployees = 0;
                    for (let item of order.supportedFeatures) {
                        if (item.feature.featureId === 'EMPLOYEE_MANAGE') {
                            numEmployees = parseInt(item.value);
                            break;
                        }
                    }

                    this.setState({
                        order: order,
                        numEmployees: numEmployees
                    })
                } else {
                    alert(response.data.returnMessage);
                    this.setState({
                        error: true,
                    });
                }
            })
            .catch(error => {
                console.error(error);
                this.setState({
                    error: true,
                });
            })
            .finally(() => {
                this.setState({
                    isLoading: false,
                })
            });
    };

    setParams = (val) => {
        this.setState({
            error: false,
        });

        try {
            const values = queryString.parse(val);
            const {params} = values;

            this.setState({
                params: JSON.parse(params)
            }, () => this.createOrder());
        } catch (e) {
            this.setState({
                error: true,
            });
        }
    };

    componentWillReceiveProps(nextProps, nextContext) {
        if (this.props.location.search !== nextProps.location.search) {
            this.setParams(nextProps.location.search);
        }
    }

    componentDidMount() {
        this.setParams(this.props.location.search);
    }

    handlePayButtonClick = () => {
        const {paymentName, paymentId, paymentCycleType, order} = this.state;
        if (paymentName === '') {
            alert('Tên người thanh toán không hợp lệ');
            return;
        }

        if (paymentId === '') {
            alert('Mã thanh toán không hợp lệ');
            return;
        }

        const confirm = window.confirm('Bạn xác nhận thanh toán cho đơn hàng ?');
        if (confirm) {
            const data = {
                paymentName,
                paymentId,
                paymentCycleType,
                orderId: order.orderId
            };

            axios.post(submitTransUrl, data, {})
                .then(response => {
                    if (response.data.returnCode === 1) {
                        alert(response.data.returnMessage);
                        setTimeout(() => {
                            window.open("", "_self");
                            window.close();
                        }, 1000);
                    } else {
                        alert(response.data.returnMessage);
                    }
                })
                .catch(error => {
                    alert("Hệ thống có lỗi. Vui lòng thử lại sau");
                    console.error(error);
                    this.setState({
                        error: true,
                    });
                })
        }
    };

    formatCurrency = (amount) => {
        return (new Intl.NumberFormat(this.locale, {
            style: 'currency',
            currency: this.currency,
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(amount))
    };

    calcLineItemsTotal = () => {
        const {order, numEmployees} = this.state;
        let total = 0;

        for (let item of order.supportedFeatures) {
            if (item.value === false) {
                continue;
            }

            const {feature} = item;
            if (feature.unitPrice > 0) {
                if (feature.calcByEachEmployee) {
                    total += feature.unitPrice * numEmployees;
                } else {
                    total += feature.unitPrice;
                }
            }
        }
        return total;
    };

    calcTaxTotal = () => {
        return this.calcLineItemsTotal() * (this.state.order.discountPercentage / 100)
    };

    calcGrandTotal = () => {
        return this.calcLineItemsTotal() - this.calcTaxTotal()
    };

    onChangePaymentName = (event) => {
        const value = event.target.value;
        this.setState({
            paymentName: value,
        })
    };

    onChangePaymentId = (event) => {
        const value = event.target.value;
        this.setState({
            paymentId: value,
        })
    };

    onChangePaymentCycleType = (event) => {
        const value = event.target.value;
        this.setState({
            paymentCycleType: value,
        })
    };

    render = () => {
        const {error, isLoading, order} = this.state;

        if (error) {
            return <Error/>
        }
        if (isLoading) {
            return <img src={loading}
                        style={{display: isLoading ? 'block' : 'none'}}
                        className={"center-div"}
                        alt={"loading"}/>
        }

        return (

            <div className={styles.invoice}>
                <div className={styles.brand}>
                    <img src="https://drive.google.com/uc?export=view&id=1x2tn-cRCtMJv_1qJZRLjK9firQjr1Wfo"
                         width="200px"
                         alt="Logo" className={styles.logo}/>
                </div>
                <div className={styles.addresses}>
                    <div className={styles.from}>
                        <strong>Hệ thống HTCC</strong><br/>
                        Đại học Khoa Học Tự Nhiên, 227, Nguyễn Văn Cừ, Phường 4, Quận 5, Hồ Chí Minh<br/>
                        (84) 286 2884 499
                    </div>
                    <div>
                        <div className={`${styles.valueTable} ${styles.to}`}>
                            <div className={styles.row}>
                                <div className={styles.label}>Mã công ty #</div>
                                <div className={styles.value}>{order.companyId}</div>
                            </div>
                            <div className={styles.row}>
                                <div className={styles.label}>Mã đơn hàng #</div>
                                <div className={styles.value}>{order.orderId}</div>
                            </div>
                            <div className={styles.row}>
                                <div className={styles.label}>Ngày</div>
                                <div className={`${styles.value} ${styles.date}`}>{order.date}</div>
                            </div>
                        </div>
                    </div>
                </div>
                <h2 style={{margin: '20px 0'}}>Chi tiết đơn hàng</h2>

                <LineItems
                    items={order.supportedFeatures}
                    currencyFormatter={this.formatCurrency}
                />

                <div className={styles.totalContainer}>
                    <form>
                        <div className={styles.valueTable}>
                            <div className={styles.row}>
                                <div className={styles.label} style={{width: '110%'}}>Khuyến mãi (%)</div>
                                <div className={styles.value} style={{textAlign: 'center'}}>
                                    {order.discountPercentage}
                                </div>
                            </div>
                        </div>
                        <h3 style={{margin: '30px 0'}}>Thông tin thanh toán</h3>
                        <div className={styles.valueTable}>
                            <div className={styles.row}>
                                <div style={{display: 'inline'}}>Tên người thanh toán</div>
                                <input name="paymentName" type="text" required onChange={this.onChangePaymentName}/>
                            </div>
                            <div className={styles.row}>
                                <div style={{display: 'inline'}}>Mã thanh toán</div>
                                <input name="paymentId" type="text" required onChange={this.onChangePaymentId}/>
                            </div>
                            <div className={styles.row}>
                                <div style={{display: 'inline'}}>Loại thanh toán</div>
                                <select name="paymentCycleType" id="paymentCycleType"
                                        onChange={this.onChangePaymentCycleType}>
                                    <option value="1">Theo tháng</option>
                                    <option value="2">Theo năm</option>
                                </select>
                            </div>
                        </div>
                    </form>
                    <form>
                        <div className={styles.valueTable}>
                            <div className={styles.row}>
                                <div className={styles.label}>Tạm Tính</div>
                                <div
                                    className={`${styles.value} ${styles.currency}`}>{this.formatCurrency(this.calcLineItemsTotal())}</div>
                            </div>
                            <div className={styles.row}>
                                <div className={styles.label}>Giảm giá ({this.state.order.discountPercentage}%)</div>
                                <div
                                    className={`${styles.value} ${styles.currency}`}>{this.formatCurrency(this.calcTaxTotal())}</div>
                            </div>
                            <div className={styles.row}>
                                <div className={styles.label}>Tổng Tiền</div>
                                <div
                                    className={`${styles.value} ${styles.currency}`}>{this.formatCurrency(this.calcGrandTotal())}</div>
                            </div>
                            <div className={styles.row}>
                                <div className={styles.label}>Trả theo tháng</div>
                                <div
                                    className={`${styles.value} ${styles.currency}`}>{this.formatCurrency(this.calcGrandTotal() / 12)}</div>
                            </div>
                        </div>
                    </form>
                </div>

                <div className={styles.pay}>
                    <button className={styles.payNow} onClick={this.handlePayButtonClick}>Thanh toán</button>
                </div>
            </div>
        )
    }
}

export default Invoice;
