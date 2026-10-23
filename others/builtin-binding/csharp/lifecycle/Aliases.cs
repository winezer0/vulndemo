using Choice = Service;
using Factory = Service;
class Aliases
{
    Other Choice;
    int Run()
    {
        Choice.Hit(1);
        return Factory.Make(1);
    }
}
