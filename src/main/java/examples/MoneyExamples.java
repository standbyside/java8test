package examples;

import com.google.common.collect.Lists;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetarySummaryStatistics;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryOperator;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class MoneyExamples {

  public static void getCurrency() {

    // 据货币代码来获取货币单位
    CurrencyUnit currencyUnit = Monetary.getCurrency("USD");

    // 亦或根据国家及地区来获取货币单位
    CurrencyUnit unit = Monetary.getCurrency(Locale.US);
  }

  /**
   * Money与FastMoney是JavaMoney库中MonetaryAmount的两种实现。
   * Money是默认实现，它使用BigDecimal来存储金额。
   * FastMoney是可选的另一个实现，它用long类型来存储金额。
   * 根据文档来看，FastMoney上的操作要比Money的快10到15倍左右。
   * 然而，FastMoney的金额大小与精度都受限于long类型
   */
  public static void getMoney() {

    CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.US);
    // 金额表示
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency(currencyUnit).setNumber(200).create();

    Money usMoney = Money.of(12, currencyUnit);
    Money eurMoney = Money.of(1, "EUR");

    FastMoney fastMoney = FastMoney.of(2, currencyUnit);
  }

  public static void test() {

    MonetaryAmountFactory factory = Monetary.getDefaultAmountFactory();

    CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.US);
    // 金额表示
    MonetaryAmount fstAmtUSD = factory.setCurrency(currencyUnit).setNumber(200).create();
    // 货币计算
    MonetaryAmount oneDolar = factory.setCurrency(currencyUnit).setNumber(1).create();

    // 加
    MonetaryAmount[] monetaryAmounts = new MonetaryAmount[] {
        Money.of(100, "CHF"),
        Money.of(10.20, "CHF"),
        Money.of(1.15, "CHF")};
    Money sumAmtCHF = Money.of(0, "CHF");
    for (MonetaryAmount monetaryAmount : monetaryAmounts) {
      sumAmtCHF = sumAmtCHF.add(monetaryAmount);
    }
    // 减
    Money calcAmtUSD = Money.of(1, "USD").subtract(fstAmtUSD);
    // 乘
    MonetaryAmount multiplyAmount = oneDolar.multiply(0.25);
    oneDolar.multiply(BigDecimal.valueOf(11));
    // 除
    MonetaryAmount divideAmount = oneDolar.divide(0.25);

    // 四舍五入
    MonetaryAmount fstAmtEUR = factory.setCurrency("EUR").setNumber(1.30473908).create();
    MonetaryAmount roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());

    MonetaryAmount oneDollar = factory.setCurrency("USD").setNumber(1).create();

    // 货币格式化以及解析
    MonetaryAmountFormat formatUS = MonetaryFormats.getAmountFormat(Locale.US);
    String usFormatted = formatUS.format(oneDollar);
  }


  /**
   * 过滤
   */
  public static void filter() {
    List<MonetaryAmount> amounts = getList();

    CurrencyUnit yen = Monetary.getCurrency("JPY");

    CurrencyUnit dollar = Monetary.getCurrency("USD");

    // 根据货币过滤，只返回美金
    List<MonetaryAmount> USDs = amounts.stream()
        .filter(MonetaryFunctions.isCurrency(dollar))
        .collect(Collectors.toList());

    // 根据货币过滤，只返回美金和日元
    List<MonetaryAmount> USDsAndJPYs = amounts.stream()
        .filter(MonetaryFunctions.isCurrency(dollar, yen))
        .collect(Collectors.toList());

    MonetaryAmount ten = Money.of(1000, dollar);

    List<MonetaryAmount> greaterThanTenUSDs = amounts.stream()
        .filter(MonetaryFunctions.isCurrency(dollar))
        .filter(MonetaryFunctions.isGreaterThan(ten))
        .collect(Collectors.toList());
  }

  /**
   * 排序
   */
  public static void sort() {
    List<MonetaryAmount> amounts = getList();

    // Sorting by number value
    List<MonetaryAmount> sortedByAmount = amounts.stream()
        .sorted(MonetaryFunctions.sortNumber())
        .collect(Collectors.toList());

    // Sorting by CurrencyUnit
    List<MonetaryAmount> sortedByCurrencyUnit = amounts.stream()
        .sorted(MonetaryFunctions.sortCurrencyUnit())
        .collect(Collectors.toList());
  }

  /**
   * 分组
   */
  public static void group() {
    List<MonetaryAmount> amounts = getList();
    // 按货币单位进行分组
    Map<CurrencyUnit, List<MonetaryAmount>> groupedByCurrency = amounts.stream()
        .collect(MonetaryFunctions.groupByCurrencyUnit());
  }

  /**
   * 汇总
   */
  public static void summary() {
    List<MonetaryAmount> amounts = getList();
    CurrencyUnit dollar = Monetary.getCurrency("USD");

    // 分组并进行汇总
    Map<CurrencyUnit, MonetarySummaryStatistics> summary = amounts.stream()
        .collect(MonetaryFunctions.groupBySummarizingMonetary())
        .get();

    MonetarySummaryStatistics dollarSummary = summary.get(dollar);
    MonetaryAmount average = dollarSummary.getAverage();
    MonetaryAmount min = dollarSummary.getMin();
    MonetaryAmount max = dollarSummary.getMax();
    MonetaryAmount sum = dollarSummary.getSum();
    long count = dollarSummary.getCount();
  }

  /**
   * MonetaryFunctions提供了归约函数，可以用来获取最大值，最小值，以及求和：
   */
  public static void reduce() {
    List<MonetaryAmount> amounts = Lists.newArrayList(
        Money.of(10, "EUR"),
        Money.of(7.5, "EUR"),
        Money.of(12, "EUR")
    );

    // "EUR 12"
    Optional<MonetaryAmount> max = amounts.stream().reduce(MonetaryFunctions.max());
    // "EUR 7.5"
    Optional<MonetaryAmount> min = amounts.stream().reduce(MonetaryFunctions.min());
    // "EUR 29.5"
    Optional<MonetaryAmount> sum = amounts.stream().reduce(MonetaryFunctions.sum());
  }

  /**
   * MonetaryAmount提供了一个非常友好的扩展点叫作MonetaryOperator。
   * MonetaryOperator是一个函数式接口，它接收一个MonetaryAmount入参并返回一个新的MonetaryAmount对象
   */
  public static void operator() {
    MonetaryOperator tenPercentOperator = (MonetaryAmount amount) -> {
      BigDecimal baseAmount = amount.getNumber().numberValue(BigDecimal.class);
      BigDecimal tenPercent = baseAmount.multiply(new BigDecimal("0.1"));
      return Money.of(tenPercent, amount.getCurrency());
    };

    MonetaryAmount dollars = Money.of(12.34567, "USD");
    MonetaryAmount tenPercentDollars = dollars.with(tenPercentOperator);
  }

  /**
   * 【汇率】
   * <p>
   * 货币兑换率可以通过ExchangeRateProvider来获取。
   * JavaMoney自带了多个不同的ExchangeRateProvider的实现。
   * 其中最重要的两个是ECBCurrentRateProvider与 IMFRateProvider。
   * <p>
   * ECBCurrentRateProvider查询的是欧洲中央银行(European Central Bank,ECB)的数据
   * 而IMFRateProvider查询的是国际货币基金组织（International Monetary Fund，IMF）的汇率。
   */
  public static void exchangeRate() {
    // get the default ExchangeRateProvider (CompoundRateProvider)
    ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
    // get a specific ExchangeRateProvider (here ECB)
    ExchangeRateProvider ecbExchangeRateProvider = MonetaryConversions.getExchangeRateProvider("ECB");
    // get the names of the default provider chain [IDENT, ECB, IMF, ECB-HIST]
    List<String> defaultProviderChain = MonetaryConversions.getDefaultConversionProviderChain();

    ExchangeRate rate = exchangeRateProvider.getExchangeRate("EUR", "USD");

    // 1.2537 (at time writing)
    NumberValue factor = rate.getFactor();
    // EUR
    CurrencyUnit baseCurrency = rate.getBaseCurrency();
    // USD
    CurrencyUnit targetCurrency = rate.getCurrency();
  }

  /**
   * 【币种转换】
   * <p>
   * 不同货币间的转换可以通过ExchangeRateProvider返回的CurrencyConversions来完成。
   * 注意:CurrencyConversion也实现了MonetaryOperator接口。正如其它操作一样，它也能通过MonetaryAmount.with()方法来调用
   */
  public static void currencyConversion() {
    CurrencyConversion dollarConversion = MonetaryConversions.getConversion("USD");
    CurrencyConversion ecbDollarConversion = MonetaryConversions.getExchangeRateProvider("ECB").getCurrencyConversion("USD");
    MonetaryAmount inDollar = Money.of(10, "EUR").with(dollarConversion);
  }

  private static List<MonetaryAmount> getList() {
    List<MonetaryAmount> amounts = new ArrayList<>();
    amounts.add(Money.of(2000.00, "EUR"));
    amounts.add(Money.of(4200.00, "USD"));
    amounts.add(Money.of(700.00, "USD"));
    amounts.add(Money.of(13.37, "JPY"));
    amounts.add(Money.of(188000.80, "USD"));
    return amounts;
  }
}
