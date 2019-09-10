<#setting number_format="computer">

<body>
<h1>Sorry...</h1>

<p>Dear ${customer.name}, <br/><br/>
your order #${order.id} is <b>NOT payed successfully</b>!
</p>

<p>
The payment failure message is: ${paymentStatus.errorMessage}
</p>

<h3>Order details</h3>

<p>Total amount: ${order.totalAmount} USD</p>

<h4>Items</h4>

<table width="100%" border="1px">
        <thead>
        <tr>
                <th>Product</th>
                <th>Qty</th>
                <th>Subtotal</th>
        </tr>
        </thead>

        <#list order.orderItems as x >
        <tr>
                <td>${x.productName}</td>
                <td>${x.quantity}</td>
                <td>${x.subtotal} USD</td>
        </tr>
</#list>


<tr>
        <td>SHIPPING COST</td>
        <td>&nbsp;</td>
        <td>${shipping.shippingCost} USD</td>
</tr>

<tr>
        <td><strong>TOTAL</strong></td>
        <td>&nbsp;</td>
        <td><strong>${order.totalAmount} USD</strong></td>
</tr>

</table>

<hr/>
Your Shop.Turing.Com

</body>