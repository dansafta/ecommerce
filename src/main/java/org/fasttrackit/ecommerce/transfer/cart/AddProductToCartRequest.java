package org.fasttrackit.ecommerce.transfer.cart;

public class AddProductToCartRequest {

    private long custometId;
    private long productId;

    public long getCustometId() {
        return custometId;
    }

    public void setCustometId(long custometId) {
        this.custometId = custometId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
