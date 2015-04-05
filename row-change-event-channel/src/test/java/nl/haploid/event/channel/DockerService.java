package nl.haploid.event.channel;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Service
public class DockerService {

    @Autowired
    private DockerClient docker;

    public String createTempDirectory() {
        final String tempDir;
        try {
            tempDir = Files.createTempDirectory("volume").toFile().getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Could not create temp directory!", e);
        }
        return tempDir;
    }

    public String startContainer(final String image, final String name,
                                  final Ports ports, final Bind bind) {
        final CreateContainerCmd createContainer = docker.createContainerCmd(image)
                .withName(name);
        for (final Map.Entry<ExposedPort, Ports.Binding[]> entry : ports.getBindings().entrySet()) {
            createContainer.withExposedPorts(entry.getKey());
        }
        final CreateContainerResponse container = createContainer.exec();
        final String id = container.getId();
        docker.startContainerCmd(id)
                .withBinds(bind)
                .withPortBindings(ports)
                .exec();
        docker.waitContainerCmd(id);
        return id;
    }

    public void stopContainer(final String containerName) {
        final List<Container> containers = docker
                .listContainersCmd()
                .withShowAll(true)
                .exec();
        for (final Container container : containers) {
            for (final String name : container.getNames()) {
                if (name.equals(String.format("/%s", containerName))) {
                    if (docker.inspectContainerCmd(container.getId()).exec().getState().isRunning()) {
                        docker.stopContainerCmd(container.getId()).exec();
                    }
                    docker.removeContainerCmd(container.getId()).exec();
                }
            }
        }
    }
}
