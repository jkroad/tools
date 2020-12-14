package com.ismayfly.coins.tools.model.po;

public class McShopCodeMapping {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mc_shop_code_mapping.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mc_shop_code_mapping.main_category
     *
     * @mbggenerated
     */
    private String mainCategory;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mc_shop_code_mapping.secondary_category
     *
     * @mbggenerated
     */
    private String secondaryCategory;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mc_shop_code_mapping.categories
     *
     * @mbggenerated
     */
    private String categories;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mc_shop_code_mapping.code
     *
     * @mbggenerated
     */
    private Integer code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mc_shop_code_mapping.code_value
     *
     * @mbggenerated
     */
    private String codeValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mc_shop_code_mapping.shop_details
     *
     * @mbggenerated
     */
    private String shopDetails;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mc_shop_code_mapping.id
     *
     * @return the value of mc_shop_code_mapping.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mc_shop_code_mapping.id
     *
     * @param id the value for mc_shop_code_mapping.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mc_shop_code_mapping.main_category
     *
     * @return the value of mc_shop_code_mapping.main_category
     *
     * @mbggenerated
     */
    public String getMainCategory() {
        return mainCategory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mc_shop_code_mapping.main_category
     *
     * @param mainCategory the value for mc_shop_code_mapping.main_category
     *
     * @mbggenerated
     */
    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory == null ? null : mainCategory.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mc_shop_code_mapping.secondary_category
     *
     * @return the value of mc_shop_code_mapping.secondary_category
     *
     * @mbggenerated
     */
    public String getSecondaryCategory() {
        return secondaryCategory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mc_shop_code_mapping.secondary_category
     *
     * @param secondaryCategory the value for mc_shop_code_mapping.secondary_category
     *
     * @mbggenerated
     */
    public void setSecondaryCategory(String secondaryCategory) {
        this.secondaryCategory = secondaryCategory == null ? null : secondaryCategory.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mc_shop_code_mapping.categories
     *
     * @return the value of mc_shop_code_mapping.categories
     *
     * @mbggenerated
     */
    public String getCategories() {
        return categories;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mc_shop_code_mapping.categories
     *
     * @param categories the value for mc_shop_code_mapping.categories
     *
     * @mbggenerated
     */
    public void setCategories(String categories) {
        this.categories = categories == null ? null : categories.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mc_shop_code_mapping.code
     *
     * @return the value of mc_shop_code_mapping.code
     *
     * @mbggenerated
     */
    public Integer getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mc_shop_code_mapping.code
     *
     * @param code the value for mc_shop_code_mapping.code
     *
     * @mbggenerated
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mc_shop_code_mapping.code_value
     *
     * @return the value of mc_shop_code_mapping.code_value
     *
     * @mbggenerated
     */
    public String getCodeValue() {
        return codeValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mc_shop_code_mapping.code_value
     *
     * @param codeValue the value for mc_shop_code_mapping.code_value
     *
     * @mbggenerated
     */
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mc_shop_code_mapping.shop_details
     *
     * @return the value of mc_shop_code_mapping.shop_details
     *
     * @mbggenerated
     */
    public String getShopDetails() {
        return shopDetails;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mc_shop_code_mapping.shop_details
     *
     * @param shopDetails the value for mc_shop_code_mapping.shop_details
     *
     * @mbggenerated
     */
    public void setShopDetails(String shopDetails) {
        this.shopDetails = shopDetails == null ? null : shopDetails.trim();
    }

    @Override
    public String toString() {
        return "McShopCodeMapping{" +
                "id=" + id +
                ", mainCategory='" + mainCategory + '\'' +
                ", secondaryCategory='" + secondaryCategory + '\'' +
                ", categories='" + categories + '\'' +
                ", code=" + code +
                ", codeValue='" + codeValue + '\'' +
                ", shopDetails='" + shopDetails + '\'' +
                '}';
    }
}