package com.ismayfly.coins.tools.model.po;

public class MpPayConfigBank {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mp_pay_config_bank.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mp_pay_config_bank.card_name
     *
     * @mbggenerated
     */
    private String cardName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mp_pay_config_bank.card_type
     *
     * @mbggenerated
     */
    private String cardType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mp_pay_config_bank.code_type
     *
     * @mbggenerated
     */
    private Integer codeType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mp_pay_config_bank.code
     *
     * @mbggenerated
     */
    private Integer code;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mp_pay_config_bank.id
     *
     * @return the value of mp_pay_config_bank.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mp_pay_config_bank.id
     *
     * @param id the value for mp_pay_config_bank.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mp_pay_config_bank.card_name
     *
     * @return the value of mp_pay_config_bank.card_name
     *
     * @mbggenerated
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mp_pay_config_bank.card_name
     *
     * @param cardName the value for mp_pay_config_bank.card_name
     *
     * @mbggenerated
     */
    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mp_pay_config_bank.card_type
     *
     * @return the value of mp_pay_config_bank.card_type
     *
     * @mbggenerated
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mp_pay_config_bank.card_type
     *
     * @param cardType the value for mp_pay_config_bank.card_type
     *
     * @mbggenerated
     */
    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mp_pay_config_bank.code
     *
     * @return the value of mp_pay_config_bank.code
     *
     * @mbggenerated
     */
    public Integer getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mp_pay_config_bank.code
     *
     * @param code the value for mp_pay_config_bank.code
     *
     * @mbggenerated
     */
    public void setCode(Integer code) {
        this.code = code;
    }
}