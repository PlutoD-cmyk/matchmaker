package org.plutodjava.matchmaker.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.plutodjava.matchmaker.db.domain.TbUser;

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
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserIntentionVo extends TbUser {
    private IntentionJsonVo intention;

}
