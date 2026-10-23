class Captures
{
    int Run(Service client, int input)
    {
        int Local(int value) { return client.Hit(value); }
        System.Func<int, int> first = value => client.Hit(value); System.Func<int, int> second = value => client.Hit(value);
        return Local(input);
    }
}
