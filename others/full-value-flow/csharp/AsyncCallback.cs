using System.Threading.Tasks;

class AsyncCallback {
    // async_callback binds a real delegate return to the saved task.
    static async Task<string> async_callback(string async_callbackInput) {
        System.Func<string> produce = () => async_callbackInput;
        Task<string> pending = Task.Run(produce);
        string async_callbackOut = await pending;
        return async_callbackOut;
    }
    // async_callback_isolation consumes only the independent safe task.
    static async Task<string> async_callback_isolation(string async_callback_isolationInput) {
        System.Func<string> tainted = () => async_callback_isolationInput;
        System.Func<string> safe = () => "safe";
        Task<string> first = Task.Run(tainted);
        Task<string> second = Task.Run(safe);
        string async_callback_isolationOut = await second;
        return async_callback_isolationOut;
    }
}
