class Generic
{
    int Run<Service>(Service value) where Service : global::Service
    {
        return value.Hit(1);
    }
}
