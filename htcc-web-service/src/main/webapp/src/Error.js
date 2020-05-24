import React from 'react';

const Error = () => {
    return (<>
        <div id="notfound">
            <div className="notfound">
                <div className="notfound-404">
                    <h1>500</h1>
                </div>
                <h2>Hệ thống có lỗi. Vui lòng thử lại sau.</h2>
                <p>Đã xảy ra lỗi khi tải dữ liệu. Vui lòng tải lại trang</p>
                <p>hoặc liên hệ quản trị viên để được hỗ trợ.</p>
                {/*eslint-disable-next-line*/}
                <a href="javascript:window.location.reload(true)">Tải lại trang</a>
            </div>
        </div>
    </>)
};

export default Error;
