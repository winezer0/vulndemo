class Caller
{
    Service slot;
    int Run(int input)
    {
        slot.Hit(input);
        {
            Other slot = new Other();
            slot.Hit(input);
        }
        return slot.Hit(input);
    }
}
