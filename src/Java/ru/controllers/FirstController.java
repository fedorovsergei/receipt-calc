package ru.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.models.Position;
import ru.models.Room;
import ru.service.ServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class FirstController {

    private final ServiceImpl service;

    public FirstController(ServiceImpl service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage() {
        return "startPage";
    }

    @GetMapping("/newRoom")
    public String newRoom(Model model) {
        model.addAttribute("room", new Room());
        return "newRoom";
    }

    @PostMapping("/createRoom")
    public String createRoom(@ModelAttribute("room") Room room) {
        String key = service.createRoom(room);
        return "redirect:/rooms/existingRoom?roomKey=" + key;
    }

    @GetMapping("/existingRoom")
    public String existingRoom(@RequestParam("roomKey") String key, Model model) {
        if (service.getRoomForKey(key) == null) {
            return "notFound";
        }
        model.addAttribute("room", service.getRoomForKey(key));
        List<Position> positions = service.getPositions(key);
        if (!positions.isEmpty()) {
            model.addAttribute("positions", positions);
        }
        model.addAttribute("position", new Position());
        model.addAttribute("positionsUser", service.createMap(key));
        model.addAttribute("sum", service.getSum(key));
        return "existingRoom";
    }

    @GetMapping("/allRooms")
    public String allRooms(Model model) {
        model.addAttribute("rooms", service.getRooms());
        return "allRooms";
    }

    @PatchMapping("/changeName/{key}")
    public String changeName(@ModelAttribute("room") Room room, @PathVariable("key") String key) {
        service.changeRoomName(room, key);
        return "redirect:/rooms/existingRoom?roomKey={key}";
    }

    @PostMapping("/addPosition/{key}")
    public String addPosition(@ModelAttribute("position") Position position, @PathVariable("key") String key) {
        service.addPosition(key, position);
        return "redirect:/rooms/existingRoom?roomKey={key}";
    }

    @PostMapping("/deletePosition/{key}")
    public String deletePosition(@ModelAttribute("position") Position position, @PathVariable("key") String key) {
        service.deletePosition(position.getPositionId());
        return "redirect:/rooms/existingRoom?roomKey={key}";
    }


    @PostMapping("addPositionUser/{id}")
    public String addPositionUser(@ModelAttribute("position") Position position,
                                  @PathVariable("id") String id) {
        service.addUserName(id, position.getPositionNameUser());
        return "redirect:/rooms/existingRoom?roomKey=" + service.getKeyPosition(id);
    }
}
