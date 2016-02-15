package anon.psd.device.state;

/**
 * Created by Dmitry on 17.08.2015.
 */

public class CurrentServiceState
{
    private ServiceState serviceState = ServiceState.NotInitialised;
    private ConnectionState connectionState = ConnectionState.NotAvailable;
    private ProtocolState protocolState = ProtocolState.NotAvailable;


    public byte[] toByteArray()
    {
        byte[] serialised = new byte[3];
        serialised[0] = (byte) serviceState.getInt();
        serialised[1] = (byte) connectionState.getInt();
        serialised[2] = (byte) protocolState.getInt();
        return serialised;
    }

    public static CurrentServiceState fromByteArray(byte[] serialised)
    {
        CurrentServiceState currentState = new CurrentServiceState();
        currentState.setServiceState(ServiceState.fromInteger(serialised[0]));
        currentState.setConnectionState(ConnectionState.fromInteger(serialised[1]));
        currentState.setProtocolState(ProtocolState.fromInteger(serialised[2]));
        return currentState;
    }

    public ServiceState getServiceState()
    {
        return serviceState;
    }

    public void setServiceState(ServiceState serviceState)
    {
        this.serviceState = serviceState;

        if (serviceState == ServiceState.NotInitialised)
            setConnectionState(ConnectionState.NotAvailable);
        else
            setConnectionState(ConnectionState.Disconnected);
    }


    public ConnectionState getConnectionState()
    {
        return connectionState;
    }

    public void setConnectionState(ConnectionState connectionState)
    {
        this.connectionState = connectionState;

        if (connectionState == ConnectionState.NotAvailable)
            return;

        serviceState = ServiceState.Initialised;
        if (connectionState == ConnectionState.Disconnected)
            setProtocolState(ProtocolState.NotAvailable);
        else
            setProtocolState(ProtocolState.ReadyToSend);


    }

    public ProtocolState getProtocolState()
    {
        return protocolState;
    }

    public void setProtocolState(ProtocolState protocolState)
    {
        this.protocolState = protocolState;
        if (protocolState == ProtocolState.NotAvailable)
            return;

        serviceState = ServiceState.Initialised;
        connectionState = ConnectionState.Connected;
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        CurrentServiceState anotherState = (CurrentServiceState) anotherObject;
        return this.serviceState == anotherState.serviceState &&
                this.connectionState == anotherState.connectionState &&
                this.protocolState == anotherState.protocolState;
    }


    public boolean is(ProtocolState comparingProtocolState)
    {
        return comparingProtocolState == protocolState;
    }

    public boolean is(ServiceState comparingServiceState)
    {
        return comparingServiceState == serviceState;
    }

    public boolean is(ConnectionState comparingConnectionState)
    {
        return comparingConnectionState == connectionState;
    }

}
