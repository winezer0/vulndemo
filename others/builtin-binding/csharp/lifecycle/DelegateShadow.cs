class DelegateShadow
{
    void Invoke() {}
    void Run(System.Action Invoke)
    {
        Invoke();
    }
}
