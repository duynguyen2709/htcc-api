import React, {Component} from 'react'
import PropTypes from 'prop-types'
import styles from './LineItem.module.scss'

class LineItem extends Component {
    render = () => {
        const {index, description, quantity, price} = this.props;
        return (
            <div className={styles.lineItem}>
                <div>{index + 1}</div>
                <div>
                    {description}
                </div>
                <div>
                    {quantity}
                </div>
                <div className={styles.currency}>
                    {price === 0 ? "Miễn phí" : this.props.currencyFormatter(price)}
                </div>
                <div className={styles.currency}>
                    {price === 0 ? "Miễn phí" : this.props.currencyFormatter(quantity * price)}
                </div>
            </div>
        )
    }
}

export default LineItem;

LineItem.propTypes = {
    index: PropTypes.number.isRequired,
    description: PropTypes.string,
    quantity: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    price: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
