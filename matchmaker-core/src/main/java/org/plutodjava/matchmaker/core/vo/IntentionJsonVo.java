package org.plutodjava.matchmaker.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * {
 *     "ageRange":"10-20",
 *     "minimumEducation":"专科",
 *     "heightRange":"150-165",
 *     "weightRange":"100-120",
 *     "industry":"金融,IT",
 *     "incomeRange":"10-20",
 *     "place":"北京",
 *     "marriage":"未婚"
 * }
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntentionJsonVo {
    private String ageRange;

    private String minimumEducation;

    private String heightRange;

    private String weightRange;

    private String industry;

    private String incomeRange;

    private String place;

    private String marriage;

}
