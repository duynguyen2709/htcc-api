(this["webpackJsonphtcc-web-service"]=this["webpackJsonphtcc-web-service"]||[]).push([[0],{1:function(e,t,a){e.exports={valueTable:"Invoice_valueTable__3F-Br",row:"Invoice_row__1Da4d",label:"Invoice_label__3WhgD",currency:"Invoice_currency__1Z2Jl",invoice:"Invoice_invoice__1jB4A",brand:"Invoice_brand__Gplas",logo:"Invoice_logo__2C1VL",addresses:"Invoice_addresses____QeR",from:"Invoice_from__jlRXr",to:"Invoice_to__3wdLT",value:"Invoice_value__3OJFK",address:"Invoice_address__2u5RK",totalContainer:"Invoice_totalContainer__zSvXj",pay:"Invoice_pay__3ODM-",payNow:"Invoice_payNow__22a7U",footer:"Invoice_footer__1wIgD",comments:"Invoice_comments__2z3FU",closing:"Invoice_closing__aWdgZ"}},139:function(e,t,a){"use strict";a.r(t);var n=a(0),r=a.n(n),s=a(77),c=a.n(s),o=(a(65),a(142)),i=a(78),l=a.n(i),m=a(28),d=a.n(m),h=a(79);var u=()=>r.a.createElement(r.a.Fragment,null,r.a.createElement("div",{id:"notfound"},r.a.createElement("div",{className:"notfound"},r.a.createElement("div",{className:"notfound-404"},r.a.createElement("h1",null,"500")),r.a.createElement("h2",null,"H\u1ec7 th\u1ed1ng c\xf3 l\u1ed7i. Vui l\xf2ng th\u1eed l\u1ea1i sau."),r.a.createElement("p",null,"\u0110\xe3 x\u1ea3y ra l\u1ed7i khi t\u1ea3i d\u1eef li\u1ec7u. Vui l\xf2ng t\u1ea3i l\u1ea1i trang"),r.a.createElement("p",null,"ho\u1eb7c li\xean h\u1ec7 qu\u1ea3n tr\u1ecb vi\xean \u0111\u1ec3 \u0111\u01b0\u1ee3c h\u1ed7 tr\u1ee3."),r.a.createElement("a",{href:"javascript:window.location.reload(true)"},"T\u1ea3i l\u1ea1i trang")))),p=a(44),v=a.n(p);const y="".concat("/api","/genqrcode");class E extends r.a.Component{constructor(e){super(e),this.setCompanyIdAndOfficeId=e=>{const t=d.a.parse(e),a=t.companyId,n=t.officeId;this.setState({companyId:a,officeId:n},()=>{this.setNewImage()})},this.setNewImage=()=>{if(this.state.error)return;this.setState({isLoading:!0});const e=this.state,t=e.companyId,a=e.officeId,n=(new Date).getTime(),r="".concat(y,"?companyId=").concat(t,"&officeId=").concat(a,"&reqDate=").concat(n);console.log("Load new QR Code at : ".concat(n,"\nURL = ").concat(r)),this.setState({url:r,nextCountdown:n+3e5})},this.state={url:"",isLoading:!0,companyId:"",officeId:"",nextCountdown:new Date,error:!1},this.setNewImage=this.setNewImage.bind(this),this.setCompanyIdAndOfficeId=this.setCompanyIdAndOfficeId.bind(this)}componentWillReceiveProps(e,t){this.props.location.search!==e.location.search&&this.setCompanyIdAndOfficeId(e.location.search)}componentDidMount(){this.setCompanyIdAndOfficeId(this.props.location.search),this.task=l.a.schedule("*/5 * * * *",()=>{this.setNewImage()},null)}render(){const e=this.state,t=e.url,a=e.nextCountdown,n=e.error,s=e.isLoading;return n?r.a.createElement(u,null):r.a.createElement(r.a.Fragment,null,r.a.createElement("div",null,r.a.createElement("img",{src:v.a,style:{display:s?"block":"none"},className:"center-div",alt:"loading"}),r.a.createElement("img",{alt:"qrcode",style:{display:s?"none":"block"},className:"center-div",src:t,onLoad:e=>{this.setState({isLoading:!1})},onError:e=>{this.task.destroy(),this.setState({error:!0})}})),r.a.createElement("div",{className:"countdown",style:{display:s?"none":"block"}},r.a.createElement(h.a,{date:a})))}}var g=Object(o.a)(E);const f=Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));function w(e,t){navigator.serviceWorker.register(e).then(e=>{e.onupdatefound=()=>{const a=e.installing;a.onstatechange=()=>{"installed"===a.state&&(navigator.serviceWorker.controller?(console.log("New content is available and will be used when all tabs for this page are closed. See http://bit.ly/CRA-PWA."),t&&t.onUpdate&&t.onUpdate(e)):(console.log("Content is cached for offline use."),t&&t.onSuccess&&t.onSuccess(e)))}}}).catch(e=>{console.error("Error during service worker registration:",e)})}var I=a(141),N=a(140),_=a(27),b=a(43),C=a(40),T=a.n(C);const P="".concat("/api","/resetpassword");class k extends r.a.Component{constructor(e){super(e),this.resetPassword=e=>{const t=e.password,a=this.state,n=a.clientId,r=a.username,s=a.token;let c=this.state.companyId;c||(c="");const o={clientId:n,companyId:c,username:r,password:t,token:s};T.a.post(P,o,{}).then(e=>{1===e.data.returnCode?(alert(e.data.returnMessage),setTimeout(()=>{window.open("","_self"),window.close()},1e3)):alert(e.data.returnMessage)}).catch(e=>{alert("H\u1ec7 th\u1ed1ng c\xf3 l\u1ed7i. Vui l\xf2ng th\u1eed l\u1ea1i sau"),console.error(e)})},this.setParams=e=>{this.setState({error:!1});const t=d.a.parse(e),a=t.companyId,n=t.username,r=t.token;let s=parseInt(t.clientId);!(s<1||s>3)&&n&&""!==n&&r&&""!==r&&(2!==s&&1!==s||a&&""!==a)?this.setState({companyId:a,username:n,clientId:s,token:r}):this.setState({error:!0})},this.state={companyId:"",clientId:0,username:"",token:"",error:!1},this.setParams=this.setParams.bind(this),this.resetPassword=this.resetPassword.bind(this)}componentWillReceiveProps(e,t){this.props.location.search!==e.location.search&&this.setParams(e.location.search)}componentDidMount(){this.setParams(this.props.location.search)}render(){return this.state.error?r.a.createElement(u,null):r.a.createElement("div",{className:"jumbotron"},r.a.createElement("div",{className:"container"},r.a.createElement("div",{className:"row"},r.a.createElement("div",{className:"col-md-6 offset-md-3"},r.a.createElement("h3",{style:{textAlign:"center",marginBottom:"20px"}},"Thi\u1ebft l\u1eadp l\u1ea1i m\u1eadt kh\u1ea9u"),r.a.createElement(_.d,{initialValues:{password:"",confirmPassword:""},validationSchema:b.a().shape({password:b.c().min(6,"M\u1eadt kh\u1ea9u ph\u1ea3i d\xe0i \xedt nh\u1ea5t 6 k\xed t\u1ef1").required("M\u1eadt kh\u1ea9u kh\xf4ng \u0111\u01b0\u1ee3c r\u1ed7ng"),confirmPassword:b.c().oneOf([b.b("password"),null],"M\u1eadt kh\u1ea9u kh\xf4ng kh\u1edbp").required("Nh\u1eadp l\u1ea1i m\u1eadt kh\u1ea9u kh\xf4ng \u0111\u01b0\u1ee3c r\u1ed7ng")}),onSubmit:e=>{this.resetPassword(e)},render:({errors:e,status:t,touched:a})=>r.a.createElement(_.c,null,r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"password"},"M\u1eadt kh\u1ea9u m\u1edbi"),r.a.createElement(_.b,{name:"password",type:"password",className:"form-control"+(e.password&&a.password?" is-invalid":"")}),r.a.createElement(_.a,{name:"password",component:"div",className:"invalid-feedback"})),r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"confirmPassword"},"Nh\u1eadp l\u1ea1i m\u1eadt kh\u1ea9u"),r.a.createElement(_.b,{name:"confirmPassword",type:"password",className:"form-control"+(e.confirmPassword&&a.confirmPassword?" is-invalid":"")}),r.a.createElement(_.a,{name:"confirmPassword",component:"div",className:"invalid-feedback"})),r.a.createElement("div",{className:"form-group"},r.a.createElement("button",{type:"submit",className:"btn btn-primary mr-2"},"X\xe1c nh\u1eadn"),r.a.createElement("button",{type:"reset",className:"btn btn-secondary"},"X\xf3a")))})))))}}var x=a(41),L=a(1),S=a.n(L),M=a(86),O=a(60),A=a.n(O);class F extends n.Component{constructor(...e){super(...e),this.render=()=>{const e=this.props,t=e.index,a=e.description,n=e.quantity,s=e.price;return r.a.createElement("div",{className:A.a.lineItem},r.a.createElement("div",null,t+1),r.a.createElement("div",null,a),r.a.createElement("div",null,n),r.a.createElement("div",{className:A.a.currency},0===s?"Mi\u1ec5n ph\xed":this.props.currencyFormatter(s)),r.a.createElement("div",{className:A.a.currency},0===s?"Mi\u1ec5n ph\xed":this.props.currencyFormatter(n*s)))}}}var D=F,j=a(42),W=a.n(j);class R extends n.Component{constructor(...e){super(...e),this.getNumEmployees=()=>{const e=this.props.items;var t,a=Object(x.a)(e);try{for(a.s();!(t=a.n()).done;){let e=t.value;if("EMPLOYEE_MANAGE"===e.feature.featureId)return parseInt(e.value)}}catch(n){a.e(n)}finally{a.f()}return 0},this.render=()=>{const e=this.props,t=(e.items,Object(M.a)(e,["items"]));let a=0,n=this.getNumEmployees();return r.a.createElement("form",null,r.a.createElement("div",{className:W.a.lineItems},r.a.createElement("div",{className:"".concat(W.a.gridTable)},r.a.createElement("div",{className:"".concat(W.a.row," ").concat(W.a.header)},r.a.createElement("div",null,"#"),r.a.createElement("div",null,"T\xednh n\u0103ng"),r.a.createElement("div",null,"S\u1ed1 l\u01b0\u1ee3ng"),r.a.createElement("div",null,"\u0110\u01a1n gi\xe1"),r.a.createElement("div",null,"T\u1ed5ng")),this.props.items.map((e,s)=>{if(!1===e.value)return null;let c=1;return("EMPLOYEE_MANAGE"===e.feature.featureId||e.feature.calcByEachEmployee)&&(c=n),r.a.createElement(D,Object.assign({style:{color:"red"},key:e.feature.featureId,index:a++,description:e.feature.featureName,quantity:c,price:e.feature.unitPrice},t))}))))}}}var q=R;const B="".concat("/api","/createorder"),V="".concat("/api","/submittrans");class J extends n.Component{constructor(...e){super(...e),this.locale="vi-VN",this.currency="VND",this.state={isLoading:!0,order:{},paymentName:"",paymentId:"",paymentCycleType:1},this.createOrder=()=>{const e=this.state.params;T.a.post(B,e,{}).then(e=>{if(1===e.data.returnCode){console.log(e.data.data);const r=e.data.data;let s=0;var t,a=Object(x.a)(r.supportedFeatures);try{for(a.s();!(t=a.n()).done;){let e=t.value;if("EMPLOYEE_MANAGE"===e.feature.featureId){s=parseInt(e.value);break}}}catch(n){a.e(n)}finally{a.f()}this.setState({order:r,numEmployees:s})}else alert(e.data.returnMessage),this.setState({error:!0})}).catch(e=>{console.error(e),this.setState({error:!0})}).finally(()=>{this.setState({isLoading:!1})})},this.setParams=e=>{this.setState({error:!1});try{const t=d.a.parse(e).params;this.setState({params:JSON.parse(t)},()=>this.createOrder())}catch(t){this.setState({error:!0})}},this.handlePayButtonClick=()=>{const e=this.state,t=e.paymentName,a=e.paymentId,n=e.paymentCycleType,r=e.order;if(""!==t)if(""!==a){if(window.confirm("B\u1ea1n x\xe1c nh\u1eadn thanh to\xe1n cho \u0111\u01a1n h\xe0ng ?")){const e={paymentName:t,paymentId:a,paymentCycleType:n,orderId:r.orderId};T.a.post(V,e,{}).then(e=>{1===e.data.returnCode?(alert(e.data.returnMessage),setTimeout(()=>{window.open("","_self"),window.close()},1e3)):alert(e.data.returnMessage)}).catch(e=>{alert("H\u1ec7 th\u1ed1ng c\xf3 l\u1ed7i. Vui l\xf2ng th\u1eed l\u1ea1i sau"),console.error(e),this.setState({error:!0})})}}else alert("M\xe3 thanh to\xe1n kh\xf4ng h\u1ee3p l\u1ec7");else alert("T\xean ng\u01b0\u1eddi thanh to\xe1n kh\xf4ng h\u1ee3p l\u1ec7")},this.formatCurrency=e=>new Intl.NumberFormat(this.locale,{style:"currency",currency:this.currency,minimumFractionDigits:2,maximumFractionDigits:2}).format(e),this.calcLineItemsTotal=()=>{const e=this.state,t=e.order,a=e.numEmployees;let n=0;var r,s=Object(x.a)(t.supportedFeatures);try{for(s.s();!(r=s.n()).done;){let e=r.value;if(!1===e.value)continue;const t=e.feature;t.unitPrice>0&&(t.calcByEachEmployee?n+=t.unitPrice*a:n+=t.unitPrice)}}catch(c){s.e(c)}finally{s.f()}return n},this.calcTaxTotal=()=>this.calcLineItemsTotal()*(this.state.order.discountPercentage/100),this.calcGrandTotal=()=>this.calcLineItemsTotal()-this.calcTaxTotal(),this.onChangePaymentName=e=>{const t=e.target.value;this.setState({paymentName:t})},this.onChangePaymentId=e=>{const t=e.target.value;this.setState({paymentId:t})},this.onChangePaymentCycleType=e=>{const t=e.target.value;this.setState({paymentCycleType:t})},this.render=()=>{const e=this.state,t=e.error,a=e.isLoading,n=e.order;return t?r.a.createElement(u,null):a?r.a.createElement("img",{src:v.a,style:{display:a?"block":"none"},className:"center-div",alt:"loading"}):r.a.createElement("div",{className:S.a.invoice},r.a.createElement("div",{className:S.a.brand},r.a.createElement("img",{src:"https://drive.google.com/uc?export=view&id=1x2tn-cRCtMJv_1qJZRLjK9firQjr1Wfo",width:"200px",alt:"Logo",className:S.a.logo})),r.a.createElement("div",{className:S.a.addresses},r.a.createElement("div",{className:S.a.from},r.a.createElement("strong",null,"H\u1ec7 th\u1ed1ng HTCC"),r.a.createElement("br",null),"\u0110\u1ea1i h\u1ecdc Khoa H\u1ecdc T\u1ef1 Nhi\xean, 227, Nguy\u1ec5n V\u0103n C\u1eeb, Ph\u01b0\u1eddng 4, Qu\u1eadn 5, H\u1ed3 Ch\xed Minh",r.a.createElement("br",null),"(84) 286 2884 499"),r.a.createElement("div",null,r.a.createElement("div",{className:"".concat(S.a.valueTable," ").concat(S.a.to)},r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label},"M\xe3 c\xf4ng ty #"),r.a.createElement("div",{className:S.a.value},n.companyId)),r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label},"M\xe3 \u0111\u01a1n h\xe0ng #"),r.a.createElement("div",{className:S.a.value},n.orderId)),r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label},"Ng\xe0y"),r.a.createElement("div",{className:"".concat(S.a.value," ").concat(S.a.date)},n.date))))),r.a.createElement("h2",{style:{margin:"20px 0"}},"Chi ti\u1ebft \u0111\u01a1n h\xe0ng"),r.a.createElement(q,{items:n.supportedFeatures,currencyFormatter:this.formatCurrency}),r.a.createElement("div",{className:S.a.totalContainer},r.a.createElement("form",null,r.a.createElement("div",{className:S.a.valueTable},r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label,style:{width:"110%"}},"Khuy\u1ebfn m\xe3i (%)"),r.a.createElement("div",{className:S.a.value,style:{textAlign:"center"}},n.discountPercentage))),r.a.createElement("h3",{style:{margin:"30px 0"}},"Th\xf4ng tin thanh to\xe1n"),r.a.createElement("div",{className:S.a.valueTable},r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{style:{display:"inline"}},"T\xean ng\u01b0\u1eddi thanh to\xe1n"),r.a.createElement("input",{name:"paymentName",type:"text",required:!0,onChange:this.onChangePaymentName})),r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{style:{display:"inline"}},"M\xe3 thanh to\xe1n"),r.a.createElement("input",{name:"paymentId",type:"text",required:!0,onChange:this.onChangePaymentId})),r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{style:{display:"inline"}},"Lo\u1ea1i thanh to\xe1n"),r.a.createElement("select",{name:"paymentCycleType",id:"paymentCycleType",onChange:this.onChangePaymentCycleType},r.a.createElement("option",{value:"1"},"Theo th\xe1ng"),r.a.createElement("option",{value:"2"},"Theo n\u0103m"))))),r.a.createElement("form",null,r.a.createElement("div",{className:S.a.valueTable},r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label},"T\u1ea1m T\xednh"),r.a.createElement("div",{className:"".concat(S.a.value," ").concat(S.a.currency)},this.formatCurrency(this.calcLineItemsTotal()))),r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label},"Gi\u1ea3m gi\xe1 (",this.state.order.discountPercentage,"%)"),r.a.createElement("div",{className:"".concat(S.a.value," ").concat(S.a.currency)},this.formatCurrency(this.calcTaxTotal()))),r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label},"T\u1ed5ng Ti\u1ec1n"),r.a.createElement("div",{className:"".concat(S.a.value," ").concat(S.a.currency)},this.formatCurrency(this.calcGrandTotal()))),r.a.createElement("div",{className:S.a.row},r.a.createElement("div",{className:S.a.label},"Tr\u1ea3 theo th\xe1ng"),r.a.createElement("div",{className:"".concat(S.a.value," ").concat(S.a.currency)},this.formatCurrency(this.calcGrandTotal()/12)))))),r.a.createElement("div",{className:S.a.pay},r.a.createElement("button",{className:S.a.payNow,onClick:this.handlePayButtonClick},"Thanh to\xe1n")))}}componentWillReceiveProps(e,t){this.props.location.search!==e.location.search&&this.setParams(e.location.search)}componentDidMount(){this.setParams(this.props.location.search)}}var G=J;c.a.render(r.a.createElement(I.a,null,r.a.createElement("div",null,r.a.createElement(N.a,{exact:!0,path:"/genqrcode",component:g}),r.a.createElement(N.a,{exact:!0,path:"/resetpassword",component:k}),r.a.createElement(N.a,{exact:!0,path:"/createorder",component:G}))),document.getElementById("root")),function(e){if("serviceWorker"in navigator){if(new URL("",window.location).origin!==window.location.origin)return;window.addEventListener("load",()=>{const t="".concat("","/service-worker.js");f?(!function(e,t){fetch(e).then(a=>{404===a.status||-1===a.headers.get("content-type").indexOf("javascript")?navigator.serviceWorker.ready.then(e=>{e.unregister().then(()=>{window.location.reload()})}):w(e,t)}).catch(()=>{console.log("No internet connection found. App is running in offline mode.")})}(t,e),navigator.serviceWorker.ready.then(()=>{console.log("This web app is being served cache-first by a service worker. To learn more, visit http://bit.ly/CRA-PWA")})):w(t,e)})}}()},42:function(e,t,a){e.exports={lineItems:"LineItems_lineItems__3WxKy",addItem:"LineItems_addItem__FPJ9J",gridTable:"LineItems_gridTable__DK84v",row:"LineItems_row__1CBeV",currency:"LineItems_currency__23pab",addIcon:"LineItems_addIcon__2bN4i",header:"LineItems_header__3VB8c",editable:"LineItems_editable__3YLgs"}},44:function(e,t,a){e.exports=a.p+"static/media/loading.1a197dfc.gif"},60:function(e,t,a){e.exports={lineItem:"LineItem_lineItem__OwS66",currency:"LineItem_currency__1QeDD"}},65:function(e,t,a){},89:function(e,t,a){e.exports=a(139)}},[[89,1,2]]]);
//# sourceMappingURL=main.709c1de6.chunk.js.map