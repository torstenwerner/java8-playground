import spock.lang.Specification

class Factorial extends Specification {
    def "test factorial method"() {
        expect:
        factorial(number) == result

        where:
        number | result
        0      | [0]
        1      | [1]
        2      | [2]
        3      | [3]
        4      | [2, 2]
        8      | [2, 2, 2]
        30     | [2, 3, 5]
        150    | [2, 3, 5, 5]
        210    | [2, 3, 5, 7]
    }

    def "test negative number"() {
        when:
        factorial(-1)

        then:
        def exception = thrown(RuntimeException)
        exception.message == 'number must not be negative'
    }

    def static factorial(int number) {
        if (number < 0) {
            throw new RuntimeException('number must not be negative')
        }
        if (number < 4) {
            return [number]
        }
        final int halfNumber = number / 2
        def factor1 = (2..halfNumber).find { number % it == 0 }
        if (factor1 == null) {
            return [number]
        }
        final int factor2 = number / factor1
        return [factor1].plus(factorial(factor2))
    }
}
