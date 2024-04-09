import { useFilters } from '@/store/use-filters';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from './ui/dialog';
import { Button } from './ui/button';
import { Filter } from 'lucide-react';
import { Label } from './ui/label';
import { Switch } from './ui/switch';

export const Filters = () => {
  const {
    systemMessages,
    setSystemMessages,
    highlightReported,
    setHighlightReported,
    highlightReporter,
    setHighlightReporter,
  } = useFilters();

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button>
          <Filter className='h-4 w-4 mr-2' />
          Filter
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className='text-xl font-bold'>Filter</DialogTitle>
          <DialogDescription className='text-base'>
            Passe die Suchfilter an.
          </DialogDescription>
        </DialogHeader>

        <div className='flex justify-between items-center'>
          <Label className='text-lg font-semibold'>Systemnachrichten</Label>
          <Switch
            checked={systemMessages}
            onCheckedChange={(checked) => setSystemMessages(checked)}
          />
        </div>
        <div className='flex justify-between items-center'>
          <Label className='text-lg font-semibold'>Meldenden hervorheben</Label>
          <Switch
            checked={highlightReporter}
            onCheckedChange={(checked) => setHighlightReporter(checked)}
          />
        </div>
        <div className='flex justify-between items-center'>
          <Label className='text-lg font-semibold'>
            Gemeldeten hervorheben
          </Label>
          <Switch
            checked={highlightReported}
            onCheckedChange={(checked) => setHighlightReported(checked)}
          />
        </div>
      </DialogContent>
    </Dialog>
  );
};
