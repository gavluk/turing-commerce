<#setting number_format="computer">

<body>
<h1>Congratulations!</h1>

Dear ${customer.name}, <br/><br/>
        your order #${order.id} is <b>payed successfully</b>!

<h3>Order details</h3>

<p>Total amount: ${order.totalAmount} USD</p>
<p>Shipped on: ${order.shippedOn}</p>

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

<h3>Payment details</h3>

<p>Transaction id: ${paymentStatus.id}</p>

<#assign card = paymentStatus.charge.paymentMethodDetails.card>
<p>Card: ${card.brand?capitalize} **** **** **** ${card.last4}, ${card.expMonth} / ${card.expYear}</p>

<a href="${paymentStatus.charge.receiptUrl}" type="button">Load receipt</a>


<hr/>
Your Shop.Turing.Com
</body>