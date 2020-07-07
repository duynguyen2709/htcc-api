import React, {Component} from 'react'
import PropTypes from 'prop-types'

import LineItem from './LineItem'
import styles from './LineItems.module.scss'

class LineItems extends Component {

    getNumEmployees = () => {
        const {items} = this.props;
        for (let item of items) {
            if (item.feature.featureId === 'EMPLOYEE_MANAGE') {
                return parseInt(item.value);
            }
        }

        return 0;
    };

    render = () => {
        const {items, ...functions} = this.props;
        let index = 0;
        let numEmployees = this.getNumEmployees();
        return (
            <form>
                <div className={styles.lineItems}>
                    <div className={`${styles.gridTable}`}>
                        <div className={`${styles.row} ${styles.header}`}>
                            <div>#</div>
                            <div>Tính năng</div>
                            <div>Số lượng</div>
                            <div>Đơn giá</div>
                            <div>Tổng</div>
                        </div>

                        {this.props.items.map((item, i) => {
                                if (item.value === false) {
                                    return null;
                                }
                                let quantity = 1;
                                if (item.feature.featureId === 'EMPLOYEE_MANAGE') {
                                    quantity = numEmployees;
                                } else {
                                    if (item.feature.calcByEachEmployee) {
                                        quantity = numEmployees;
                                    }
                                }

                                return <LineItem
                                    style={{color: 'red'}}
                                    key={item.feature.featureId} index={index++}
                                    description={item.feature.featureName} quantity={quantity}
                                    price={item.feature.unitPrice}
                                    {...functions}
                                />
                            }
                        )}
                    </div>
                </div>
            </form>
        )
    }
}

export default LineItems;

LineItems.propTypes = {
    items: PropTypes.array.isRequired,
    currencyFormatter: PropTypes.func.isRequired,
};


