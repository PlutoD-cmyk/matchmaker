package org.plutodjava.matchmaker.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TbFlippedMobileGroup {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.flipped_mobile
     *
     * @mbg.generated
     */
    private String flippedMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.by_flipped_mobile
     *
     * @mbg.generated
     */
    private String byFlippedMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.add_time
     *
     * @mbg.generated
     */
    private LocalDateTime addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.apply
     *
     * @mbg.generated
     */
    private Boolean apply;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.hand
     *
     * @mbg.generated
     */
    private Boolean hand;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_flipped_mobile_group.typical_url
     *
     * @mbg.generated
     */
    private String typicalUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.id
     *
     * @return the value of tb_flipped_mobile_group.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.id
     *
     * @param id the value for tb_flipped_mobile_group.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.flipped_mobile
     *
     * @return the value of tb_flipped_mobile_group.flipped_mobile
     *
     * @mbg.generated
     */
    public String getFlippedMobile() {
        return flippedMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.flipped_mobile
     *
     * @param flippedMobile the value for tb_flipped_mobile_group.flipped_mobile
     *
     * @mbg.generated
     */
    public void setFlippedMobile(String flippedMobile) {
        this.flippedMobile = flippedMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.by_flipped_mobile
     *
     * @return the value of tb_flipped_mobile_group.by_flipped_mobile
     *
     * @mbg.generated
     */
    public String getByFlippedMobile() {
        return byFlippedMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.by_flipped_mobile
     *
     * @param byFlippedMobile the value for tb_flipped_mobile_group.by_flipped_mobile
     *
     * @mbg.generated
     */
    public void setByFlippedMobile(String byFlippedMobile) {
        this.byFlippedMobile = byFlippedMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.add_time
     *
     * @return the value of tb_flipped_mobile_group.add_time
     *
     * @mbg.generated
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.add_time
     *
     * @param addTime the value for tb_flipped_mobile_group.add_time
     *
     * @mbg.generated
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.update_time
     *
     * @return the value of tb_flipped_mobile_group.update_time
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.update_time
     *
     * @param updateTime the value for tb_flipped_mobile_group.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.deleted
     *
     * @return the value of tb_flipped_mobile_group.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.deleted
     *
     * @param deleted the value for tb_flipped_mobile_group.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.apply
     *
     * @return the value of tb_flipped_mobile_group.apply
     *
     * @mbg.generated
     */
    public Boolean getApply() {
        return apply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.apply
     *
     * @param apply the value for tb_flipped_mobile_group.apply
     *
     * @mbg.generated
     */
    public void setApply(Boolean apply) {
        this.apply = apply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.hand
     *
     * @return the value of tb_flipped_mobile_group.hand
     *
     * @mbg.generated
     */
    public Boolean getHand() {
        return hand;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.hand
     *
     * @param hand the value for tb_flipped_mobile_group.hand
     *
     * @mbg.generated
     */
    public void setHand(Boolean hand) {
        this.hand = hand;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_flipped_mobile_group.typical_url
     *
     * @return the value of tb_flipped_mobile_group.typical_url
     *
     * @mbg.generated
     */
    public String getTypicalUrl() {
        return typicalUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_flipped_mobile_group.typical_url
     *
     * @param typicalUrl the value for tb_flipped_mobile_group.typical_url
     *
     * @mbg.generated
     */
    public void setTypicalUrl(String typicalUrl) {
        this.typicalUrl = typicalUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", id=").append(id);
        sb.append(", flippedMobile=").append(flippedMobile);
        sb.append(", byFlippedMobile=").append(byFlippedMobile);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", apply=").append(apply);
        sb.append(", hand=").append(hand);
        sb.append(", typicalUrl=").append(typicalUrl);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TbFlippedMobileGroup other = (TbFlippedMobileGroup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFlippedMobile() == null ? other.getFlippedMobile() == null : this.getFlippedMobile().equals(other.getFlippedMobile()))
            && (this.getByFlippedMobile() == null ? other.getByFlippedMobile() == null : this.getByFlippedMobile().equals(other.getByFlippedMobile()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getApply() == null ? other.getApply() == null : this.getApply().equals(other.getApply()))
            && (this.getHand() == null ? other.getHand() == null : this.getHand().equals(other.getHand()))
            && (this.getTypicalUrl() == null ? other.getTypicalUrl() == null : this.getTypicalUrl().equals(other.getTypicalUrl()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFlippedMobile() == null) ? 0 : getFlippedMobile().hashCode());
        result = prime * result + ((getByFlippedMobile() == null) ? 0 : getByFlippedMobile().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getApply() == null) ? 0 : getApply().hashCode());
        result = prime * result + ((getHand() == null) ? 0 : getHand().hashCode());
        result = prime * result + ((getTypicalUrl() == null) ? 0 : getTypicalUrl().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table tb_flipped_mobile_group
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        flippedMobile("flipped_mobile", "flippedMobile", "VARCHAR", false),
        byFlippedMobile("by_flipped_mobile", "byFlippedMobile", "VARCHAR", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        deleted("deleted", "deleted", "BIT", false),
        apply("apply", "apply", "BIT", false),
        hand("hand", "hand", "BIT", false),
        typicalUrl("typical_url", "typicalUrl", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tb_flipped_mobile_group
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}