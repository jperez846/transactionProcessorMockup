package org.example;

public class AuthorizationState {

    private String correlationId;
    private String currencyType;

    private long authorizedTotal;
    private long capturedTotal;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public long getAuthorizedTotal() {
        return authorizedTotal;
    }

    public void setAuthorizedTotal(long authorizedTotal) {
        this.authorizedTotal = authorizedTotal;
    }

    public long getCapturedTotal() {
        return capturedTotal;
    }

    public void setCapturedTotal(long capturedTotal) {
        this.capturedTotal = capturedTotal;
    }

    public long getRevertedTotal() {
        return revertedTotal;
    }

    public void setRevertedTotal(long revertedTotal) {
        this.revertedTotal = revertedTotal;
    }

    public long getRefundedTotal() {
        return refundedTotal;
    }

    public void setRefundedTotal(long refundedTotal) {
        this.refundedTotal = refundedTotal;
    }

    private long revertedTotal;
    private long refundedTotal;

    public AuthorizationState(String correlationId, String currencyType, long authorizedTotal) {
        this.correlationId = correlationId;
        this.currencyType = currencyType;
        this.authorizedTotal = authorizedTotal;
        capturedTotal = 0;
        revertedTotal = 0;
        refundedTotal = 0;

    }

    public long remainingToCapture() {
        return authorizedTotal - capturedTotal;
    }

    public long remainingToRevert() {
        return authorizedTotal - revertedTotal;

    }

    public long remainingToRefund() {
        return capturedTotal - refundedTotal;

    }

}


