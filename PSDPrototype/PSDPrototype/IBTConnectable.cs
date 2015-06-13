namespace PSDPrototype
{
    public interface IBTConnectable
    {
        void RecievePackageBytes(byte[] package);

        void RecieveBtConnection(IBTConnectable source, IBTConnectable partner);

    }
}