package examples;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class RegularExpressionExamples {

  /**
   * 【UserName】
   *
   *      ^[a-z0-9_-]{3,15}$
   *
   * 【Password】
   *
   *      ((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})
   *
   * 【Hex color code】
   *
   *      ^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$
   *
   * 【E-mail address】
   *
   *      ^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$
   *
   * 【Image file extension】
   *
   *      ([^\s]+(\.(?i)(jpg|png|gif|bmp))$)
   *
   * 【IP Address】
   *
   *      ^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$
   *
   * 【Time in 12 Hours format】
   *
   *      (1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)
   *
   * 【Time in 24 Hours format】
   *
   *      ([01]?[0-9]|2[0-3]):[0-5][0-9]
   *
   * 【Date】
   *
   *      (0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)
   *
   * 【HTML tag】
   *
   *      <("[^"]*"|'[^']*'|[^'">])*>
   *
   * 【HTML links】
   *
   *      (?i)<a([^>]+)>(.+?)</a>
   *      \s*(?i)href\s*=\s*(\"([^"]*\")|'[^']*'|([^'">\s]+));
   *
   */
}
